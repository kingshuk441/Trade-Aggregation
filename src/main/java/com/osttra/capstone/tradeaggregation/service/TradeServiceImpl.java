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

import com.osttra.capstone.tradeaggregation.customexception.NotFoundException;
import com.osttra.capstone.tradeaggregation.dao.CancelTradeDao;
import com.osttra.capstone.tradeaggregation.dao.PartyDao;
import com.osttra.capstone.tradeaggregation.dao.TradeDao;
import com.osttra.capstone.tradeaggregation.entity.CancelTrade;
import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
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

	private HashMap<Integer, Trade> tradeCache;
	private HashSet<String> cancelTrns;
	private boolean isFirstFetch;

	private void dataFetch() {
		if (isFirstFetch == false) {
			isFirstFetch = true;

			List<Trade> allTrades = this.tradeDao.getTrades();
			this.tradeCache = new HashMap<>();
			for (Trade t : allTrades) {
				this.tradeCache.put(t.getTradeId(), t);
			}
			this.cancelTrns = new HashSet<>();
			List<CancelTrade> cancelTrades = this.cancelDao.getCancelTrades();
			for (CancelTrade c : cancelTrades) {
				this.cancelTrns.add(c.getTradeRefNum());
			}
		}
	}

	private Trade matchingFields(TradeBody body) {
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
		Trade matchedTrade = this.matchingFields(body);
		Party party = this.partyDao.getPartyByName(body.getPartyName());
		String partyFullName = party.getPartyFullName();
		String counterPartyFullName = this.partyDao.getPartyByName(body.getCounterPartyName()).getPartyFullName();
		int institutionId = party.getInstitution().getInstitutionId();
		// unconf trade
		if (matchedTrade == null) {
			TradeBuilder tradeBuilder = new TradeBuilder(body);
			Date creationDate = new Date();
			tradeBuilder.constructNewTrade(partyFullName, counterPartyFullName, institutionId, creationDate);
			Trade newTrade = tradeBuilder.getTrade();
			this.tradeDao.saveTrade(newTrade);
			this.tradeCache.put(newTrade.getTradeId(), newTrade);
			return new CustomResponse<>("Trade added successfully!", HttpStatus.ACCEPTED.value(), newTrade);
		} else {
			// aggr trade logic

			TradeBuilder tradeBuilder = new TradeBuilder(body);
			tradeBuilder.constructNewTrade(partyFullName, counterPartyFullName, institutionId, new Date());
			// incoming trade which matched from db
			Trade incomingTrade = tradeBuilder.getTrade();

			// new updated values for aggr trades
			long sumAmount = matchedTrade.getNotionalAmount() + incomingTrade.getNotionalAmount();
			Date updatedDate = new Date();
			String newTrn = incomingTrade.getPartyName() + incomingTrade.getCounterPartyName() + updatedDate;
			tradeBuilder = new TradeBuilder(body);
			tradeBuilder.mergeNewTrade(partyFullName, counterPartyFullName, institutionId, updatedDate, newTrn,
					sumAmount, matchedTrade.getCreationTimeStamp());
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

			return new CustomResponse<>("Trade aggregated successfully!", HttpStatus.ACCEPTED.value(), newTrade);
		}
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
	public CustomResponse<CancelTrade> getCancelTrades(int id) {
		Trade trade = this.tradeDao.getTrade(id);
		if (trade == null) {
			throw new NotFoundException("trade with id " + id + " not found!");
		}
		return new CustomResponse<>("all cancel Trades fetched successfully!", HttpStatus.ACCEPTED.value(),
				trade.getAggregatedFrom());
	}

	@Override
	@Transactional
	public CustomResponse<Trade> updateTrade(int id, TradeUpdateBody body) {
		Trade t = this.tradeDao.getTrade(id);
		if (t == null) {
			throw new NotFoundException("trade with id " + id + " not found!");
		}
		TradeBuilder tb = new TradeBuilder(t, body, partyDao);
		t = this.tradeDao.saveTrade(t);
		return new CustomResponse<>("trade updated successfully!", HttpStatus.ACCEPTED.value(), t);
	}

}
