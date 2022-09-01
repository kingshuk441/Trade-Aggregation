package com.osttra.capstone.tradeaggregation.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.osttra.capstone.tradeaggregation.customexception.FoundException;
import com.osttra.capstone.tradeaggregation.customexception.NotFoundException;
import com.osttra.capstone.tradeaggregation.dao.CancelTradeDao;
import com.osttra.capstone.tradeaggregation.dao.InstitutionDao;
import com.osttra.capstone.tradeaggregation.dao.PartyDao;
import com.osttra.capstone.tradeaggregation.dao.TradeDao;
import com.osttra.capstone.tradeaggregation.entity.CancelTrade;
import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Institution;
import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.entity.Trade;
import com.osttra.capstone.tradeaggregation.objbuilder.TradeBuilder;
import com.osttra.capstone.tradeaggregation.responsebody.TradeBody;
import com.osttra.capstone.tradeaggregation.responsebody.TradeUpdateBody;

@Repository
public class TradeServiceImpl implements TradeService {
	@Autowired
	private TradeDao tradeDao;
	@Autowired
	private PartyDao partyDao;
	@Autowired
	private CancelTradeDao cancelDao;
	@Autowired
	private InstitutionDao institutionDao;

	// pk vs trade
	private HashMap<Integer, Trade> tradeCache;
	// trn vs list of trade id
	private HashMap<String, HashSet<Integer>> cancelTrns;
	private boolean isFirstFetch;

	@Transactional
	private void dataFetch() {
		if (isFirstFetch == false) {
			isFirstFetch = true;

			List<Trade> allTrades = this.tradeDao.getTrades();
			this.tradeCache = new HashMap<>();
			for (Trade t : allTrades) {
				this.tradeCache.put(t.getTradeId(), t);
			}
			this.cancelTrns = new HashMap<>();
			List<CancelTrade> cancelTrades = this.cancelDao.getCancelTrades();
			for (CancelTrade c : cancelTrades) {
				HashSet<Integer> set = this.cancelTrns.get(c.getTradeRefNum());
				if (set == null) {
					set = new HashSet<>();
				}
				set.add(c.getAggregatedTrade().getTradeId());
				this.cancelTrns.put(c.getTradeRefNum(), set);
			}
		}
	}

	@Transactional
	private Trade matchingFields(Trade body) {
		for (Integer keys : this.tradeCache.keySet()) {
			Trade t = this.tradeCache.get(keys);
			if (t.getPartyName().equals(body.getPartyName())
					&& t.getCounterPartyName().equals(body.getCounterPartyName())) {
				return t;
			}
		}
		return null;
	}

	@Override
	@Transactional
	public CustomResponse<Trade> addTrade(TradeBody body) {
		this.dataFetch();

		Party party = this.partyDao.getPartyByName(body.getPartyName());
		String partyFullName = party.getPartyFullName();
		String counterPartyFullName = this.partyDao.getPartyByName(body.getCounterPartyName()).getPartyFullName();
		int institutionId = party.getInstitution().getInstitutionId();
		TradeBuilder tradeBuilder = new TradeBuilder(body);
		Date creationDate = new Date();
		tradeBuilder.constructNewTrade(partyFullName, counterPartyFullName, institutionId, creationDate);
		Trade incomingTrade = tradeBuilder.getTrade();
		Trade matchedTrade = this.matchingFields(incomingTrade);
		// unconf trade
		if (matchedTrade == null) {
			this.tradeDao.saveTrade(incomingTrade);
			this.tradeCache.put(incomingTrade.getTradeId(), incomingTrade);
			return new CustomResponse<>("Trade added successfully!", HttpStatus.ACCEPTED.value(), incomingTrade);
		} else {
			// aggr trade logic
			Trade newTrade = this.aggregationTrade(incomingTrade, matchedTrade);
			return new CustomResponse<>("Trade aggregated successfully!", HttpStatus.ACCEPTED.value(), newTrade);
		}
	}

	@Override
	@Transactional
	public CustomResponse<CancelTrade> getCancelTrades(int id) {
		Trade trade = this.tradeDao.getTrade(id);
		if (trade == null) {
			throw new NotFoundException("trade with id " + id + " not found!");
		}
		return new CustomResponse<>("all cancel Trades fetched successfully!", HttpStatus.ACCEPTED.value(),
				trade.getAggregatedFrom());
	}

	@Transactional
	private Trade aggregationTrade(Trade incomingTrade, Trade matchedTrade) {
		Party party = this.partyDao.getPartyByName(incomingTrade.getPartyName());
		String partyFullName = party.getPartyFullName();
		String counterPartyFullName = this.partyDao.getPartyByName(incomingTrade.getCounterPartyName())
				.getPartyFullName();
		int institutionId = party.getInstitution().getInstitutionId();

		long sumAmount = matchedTrade.getNotionalAmount() + incomingTrade.getNotionalAmount();
		Date updatedDate = new Date();
		String newTrn = incomingTrade.getPartyName() + "_" + incomingTrade.getCounterPartyName() + "_" + updatedDate;
		TradeBuilder tradeBuilder = new TradeBuilder(incomingTrade);
		tradeBuilder.mergeNewTrade(partyFullName, counterPartyFullName, institutionId, updatedDate, newTrn, sumAmount,
				matchedTrade.getCreationTimeStamp());
		// aggr trade with updated values
		Trade newTrade = tradeBuilder.getTrade();

		// make the incoming and matched trade as cancel
		CancelTrade c1 = new CancelTrade(matchedTrade.getNotionalAmount(), matchedTrade.getCreationTimeStamp(),
				matchedTrade.getVersionTimeStamp(), matchedTrade.getConfirmationTimeStamp(), newTrade,
				matchedTrade.getTradeRefNum());
		CancelTrade c2 = new CancelTrade(incomingTrade.getNotionalAmount(), incomingTrade.getCreationTimeStamp(),
				incomingTrade.getVersionTimeStamp(), incomingTrade.getConfirmationTimeStamp(), newTrade,
				incomingTrade.getTradeRefNum());

		// making the list of cancel trade from prev matched trade + new cancel trades
		List<CancelTrade> prevAggrList = matchedTrade.getAggregatedFrom();
		List<CancelTrade> newAggrList = new ArrayList<>();
		if (prevAggrList != null) {
			for (CancelTrade c : prevAggrList) {
				newAggrList.add(c);
			}
		}
		newTrade.setAggregatedFrom(newAggrList);
		newTrade.addCancelTrades(c1);
		newTrade.addCancelTrades(c2);
		// save the new trade in trade table
		this.tradeDao.saveTrade(newTrade);
		// remove the matched trade from trade table
		this.tradeDao.deleteTrade(matchedTrade.getTradeId());
		// adding prev + new cancel trades in new trade
		if (newAggrList != null) {
			for (CancelTrade c : newAggrList) {
				c.setAggregatedTrade(newTrade);
			}
		}
		// adding cancel trades in cancel table
		this.cancelDao.addCancelTrade(c1);
		this.cancelDao.addCancelTrade(c2);

		// adding new trade in cache
		this.tradeCache.put(newTrade.getTradeId(), newTrade);
		// removing matched trade from cache
		this.tradeCache.remove(matchedTrade.getTradeId());

		// adding cancel trns in cancel cache
		HashSet<Integer> set = this.cancelTrns.get(c1.getTradeRefNum());
		if (set == null) {
			set = new HashSet<>();
		}
		set.add(c1.getAggregatedTrade().getTradeId());
		this.cancelTrns.put(c1.getTradeRefNum(), set);

		set = this.cancelTrns.get(c2.getTradeRefNum());
		if (set == null) {
			set = new HashSet<>();
		}
		set.add(c2.getAggregatedTrade().getTradeId());
		this.cancelTrns.put(c2.getTradeRefNum(), set);

		return newTrade;

	}

	@Override
	@Transactional
	public CustomResponse<Trade> updateTrade(int id, TradeUpdateBody body) {
		Trade t = this.tradeDao.getTrade(id);
		if (t == null) {
			throw new NotFoundException("trade with id " + id + " not found!");
		}

		TradeBuilder tb = new TradeBuilder(t, body, partyDao);
		Trade newTrade = tb.getTrade();
		newTrade.setTradeId(t.getTradeId());
		int iid = 0;
		String partyName = body.getPartyName();
		String counterPartyName = body.getCounterPartyName();
		if (partyName == null && counterPartyName != null) {
			int iid1 = newTrade.getInstitutionId();
			int iid2 = this.partyDao.getPartyByName(counterPartyName).getInstitution().getInstitutionId();
			if (iid1 == iid2) {
				throw new FoundException("counter party cant be from same institution to party");
			}

		} else if (partyName != null && counterPartyName == null) {
			iid = this.partyDao.getPartyByName(newTrade.getCounterPartyName()).getInstitution().getInstitutionId();
		}

		if (iid == newTrade.getInstitutionId()) {
			throw new FoundException("counter party cant be from same institution to party");
		}
		this.dataFetch();
		Trade matchingTrade = this.matchingFields(newTrade);
		if (matchingTrade == null) {
			newTrade = this.tradeDao.updateTrade(newTrade);
			return new CustomResponse<>("trade updated successfully!", HttpStatus.ACCEPTED.value(), newTrade);
		} else {
			Trade aggrTrade = this.aggregationTrade(newTrade, matchingTrade);
			this.tradeDao.deleteTrade(newTrade.getTradeId());
			this.tradeCache.remove(newTrade.getTradeId());
			return new CustomResponse<>("trade updated successfully with aggregation!", HttpStatus.ACCEPTED.value(),
					aggrTrade);
		}
	}

	@Override
	@Transactional
	public CustomResponse<Trade> findByPartyName(String partyName) {
		List<Trade> allTrades = this.tradeDao.getTradesByPartyName(partyName);
		return new CustomResponse<>("all trades fetched successfully!", HttpStatus.ACCEPTED.value(), allTrades);

	}

	@Override
	@Transactional
	public CustomResponse<Trade> findByInstitutionName(String institutionName) {
		Institution i = this.institutionDao.getInstitutionByName(institutionName);
		int id = i.getInstitutionId();
		List<Trade> allTrades = this.tradeDao.getTradesByInstitutionId(id);
		return new CustomResponse<>("all trades fetched successfully!", HttpStatus.ACCEPTED.value(), allTrades);
	}

	@Override
	@Transactional
	public CustomResponse<Trade> getTrades() {
		List<Trade> allTrades = this.tradeDao.getTrades();
		return new CustomResponse<>("All Trades fetched successfully!", HttpStatus.ACCEPTED.value(), allTrades);
	}

	@Override
	@Transactional
	public CustomResponse<Trade> getTrade(int id) {
		Trade trade = this.tradeDao.getTrade(id);
		if (trade == null) {
			throw new NotFoundException("trade with id " + id + " not found!");
		}
		return new CustomResponse<>("Trade fetched successfully!", HttpStatus.ACCEPTED.value(), trade);
	}

	@Override
	@Transactional
	public CustomResponse<Trade> findByTrnParty(String trn, String partyName) {
		this.dataFetch();
		if (this.cancelTrns.containsKey(trn)) {
			HashSet<Integer> set = this.cancelTrns.get(trn);
			if (set != null) {
				for (Integer keys : this.tradeCache.keySet()) {
					Trade t = this.tradeCache.get(keys);
					if (t.getPartyName().equals(partyName)) {
						return new CustomResponse<>("Trade fetched successfully!", HttpStatus.ACCEPTED.value(), t);
					}
				}
			}
		}
		for (Integer keys : this.tradeCache.keySet()) {
			Trade t = this.tradeCache.get(keys);
			if (t.getTradeRefNum().equals(trn) && t.getPartyName().equals(partyName)) {
				return new CustomResponse<>("Trade fetched successfully!", HttpStatus.ACCEPTED.value(), t);
			}

		}
		return new CustomResponse<>("Trade not found!", HttpStatus.ACCEPTED.value(), null);

	}

	@Override
	@Transactional
	public CustomResponse<Trade> findByPartyStatus(String partyName, String status) {
		this.dataFetch();
		if (status.toLowerCase().equals("cancel")) {
			List<Trade> allTrades = new ArrayList<>();
			for (int keys : this.tradeCache.keySet()) {
				Trade t = this.tradeCache.get(keys);
				if (t.getAggregatedFrom() != null && t.getPartyName().equals(partyName)) {
					allTrades.add(t);
				}
			}
			return new CustomResponse<>("Trade fetched successfully!", HttpStatus.ACCEPTED.value(), allTrades);
		} else if (status.toLowerCase().equals("unconfirm")) {
			List<Trade> allTrades = new ArrayList<>();
			for (int keys : this.tradeCache.keySet()) {
				Trade t = this.tradeCache.get(keys);
				if ((t.getAggregatedFrom() == null || t.getAggregatedFrom().size() == 0)
						&& t.getPartyName().equals(partyName)) {
					allTrades.add(t);
				}
			}
			return new CustomResponse<>("Trade fetched successfully!", HttpStatus.ACCEPTED.value(), allTrades);
		}
		return new CustomResponse<>("Trade not found!", HttpStatus.ACCEPTED.value(), null);
	}

	@Override
	@Transactional
	public CustomResponse<Trade> deleteTrade(int id) {
		Trade trade = this.tradeDao.getTrade(id);
		if (trade == null) {
			throw new NotFoundException("Trade with id " + id + " not found!");
		}
		this.tradeDao.deleteTrade(id);
		return new CustomResponse<>("Trade deleted successfully!", HttpStatus.ACCEPTED.value(), trade);
	}

}
