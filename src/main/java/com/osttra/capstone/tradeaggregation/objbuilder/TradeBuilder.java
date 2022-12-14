package com.osttra.capstone.tradeaggregation.objbuilder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.osttra.capstone.tradeaggregation.entity.CancelTrade;
import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.entity.Trade;
import com.osttra.capstone.tradeaggregation.repository.PartyRepository;
import com.osttra.capstone.tradeaggregation.responsebody.TradeBody;
import com.osttra.capstone.tradeaggregation.responsebody.TradeUpdateBody;

public class TradeBuilder {
	private int tradeId;
	private String tradeRefNum;
	private String partyName;
	private String partyFullName;
	private String counterPartyName;
	private String counterPartyFullName;
	private LocalDate tradeDate;
	private LocalDate effectiveDate;
	private String instrumentId;
	private long notionalAmount;
	private LocalDate maturityDate;
	private String currency;
	private String seller;
	private String buyer;
	private Date creationTimeStamp;
	private Date versionTimeStamp;
	private Date confirmationTimeStamp;
	private int version;
	private String status;
	private int institutionId;
	private List<CancelTrade> aggregatedFrom;

	TradeConstruct tradeSetter;

	public TradeBuilder(TradeBody body) {
		this.tradeSetter = new TradeConstruct();

		this.tradeRefNum = body.getTradeRefNum();
		this.partyName = body.getPartyName();
		this.counterPartyName = body.getCounterPartyName();
		this.tradeDate = body.getTradeDate();
		this.effectiveDate = body.getEffectiveDate();
		this.instrumentId = body.getInstrumentId();
		this.maturityDate = body.getMaturityDate();
		this.currency = body.getCurrency();
		this.seller = body.getSeller();
		this.buyer = body.getBuyer();
		this.notionalAmount = body.getNotionalAmount();

	}

	public TradeBuilder(Trade body) {
		this.tradeSetter = new TradeConstruct();
		this.tradeRefNum = body.getTradeRefNum();
		this.partyName = body.getPartyName();
		this.counterPartyName = body.getCounterPartyName();
		this.tradeDate = body.getTradeDate();
		this.effectiveDate = body.getEffectiveDate();
		this.instrumentId = body.getInstrumentId();
		this.maturityDate = body.getMaturityDate();
		this.currency = body.getCurrency();
		this.seller = body.getSeller();
		this.buyer = body.getBuyer();
		this.notionalAmount = body.getNotionalAmount();
	}

	public TradeBuilder(Trade t, TradeUpdateBody body, PartyRepository partyDao) {
		this.tradeSetter = new TradeConstruct();
		// tradeId,creTimeStamp,status,confirmTS
		this.tradeRefNum = body.getTradeRefNum() == null ? t.getTradeRefNum() : body.getTradeRefNum();
		if (body.getPartyName() != null) {
			this.partyName = body.getPartyName();
			Party p = partyDao.findByPartyName(body.getPartyName());
			this.partyFullName = p.getPartyFullName();
			this.institutionId = p.getInstitution().getInstitutionId();
		} else {
			this.partyName = t.getPartyName();
			this.partyFullName = t.getPartyFullName();
			this.institutionId = t.getInstitutionId();
		}
		if (body.getCounterPartyName() != null) {
			this.counterPartyName = body.getCounterPartyName();
			Party p = partyDao.findByPartyName(body.getCounterPartyName());
			this.counterPartyFullName = p.getPartyFullName();
		} else {
			this.counterPartyName = t.getCounterPartyName();
			this.counterPartyFullName = t.getCounterPartyFullName();
		}
		this.tradeDate = body.getTradeDate() == null ? t.getTradeDate() : body.getTradeDate();
		this.effectiveDate = body.getEffectiveDate() == null ? t.getEffectiveDate() : body.getEffectiveDate();
		this.instrumentId = body.getInstrumentId() == null ? t.getInstrumentId() : body.getInstrumentId();
		this.notionalAmount = body.getNotionalAmount() == 0 ? t.getNotionalAmount() : body.getNotionalAmount();
		this.maturityDate = body.getMaturityDate() == null ? t.getMaturityDate() : body.getMaturityDate();
		this.currency = body.getCurrency() == null ? t.getCurrency() : body.getCurrency();
		this.seller = body.getSeller() == null ? t.getSeller() : body.getSeller();
		this.buyer = body.getBuyer() == null ? t.getBuyer() : body.getBuyer();
		this.versionTimeStamp = new Date();
		this.version = t.getVersion() + 1;
		this.tradeSetter.setAggregatedFrom(t.getAggregatedFrom());
		this.creationTimeStamp = t.getCreationTimeStamp();
		this.status = t.getStatus();
		this.confirmationTimeStamp = t.getConfirmationTimeStamp();
		this.fillValues();
	}

	private void constructTrade(String partyFullName, String counterPartyFullName, int institutionId) {
		this.partyFullName = partyFullName;
		this.counterPartyFullName = counterPartyFullName;
		this.creationTimeStamp = new Date();
		this.versionTimeStamp = new Date();
		this.version = 0;

		this.institutionId = institutionId;
	}

	public void constructNewTrade(String partyFullName, String counterPartyFullName, int institutionId) {
		this.constructTrade(partyFullName, counterPartyFullName, institutionId);
		this.status = "UF";
		this.fillValues();
	}

	public void mergeNewTrade(String partyFullName, String counterPartyFullName, int institutionId, String trn,
			long notionalAmount) {
		this.constructTrade(partyFullName, counterPartyFullName, institutionId);
		this.version = 1;
		this.status = "AGG";
		this.notionalAmount = notionalAmount;
		this.tradeRefNum = trn;
		this.fillValues();
	}

	private void fillValues() {
		this.tradeSetter.setTradeRefNum(tradeRefNum);
		this.tradeSetter.setPartyName(partyName);
		this.tradeSetter.setPartyFullName(partyFullName);
		this.tradeSetter.setCounterPartyName(counterPartyName);
		this.tradeSetter.setCounterPartyFullName(counterPartyFullName);
		this.tradeSetter.setTradeDate(tradeDate);
		this.tradeSetter.setEffectiveDate(effectiveDate);
		this.tradeSetter.setInstrumentId(instrumentId);
		this.tradeSetter.setNotionalAmount(notionalAmount);
		this.tradeSetter.setMaturityDate(maturityDate);
		this.tradeSetter.setCurrency(currency);
		this.tradeSetter.setSeller(seller);
		this.tradeSetter.setBuyer(buyer);
		this.tradeSetter.setCreationTimeStamp(creationTimeStamp);
		this.tradeSetter.setVersionTimeStamp(versionTimeStamp);
		this.tradeSetter.setConfirmationTimeStamp(confirmationTimeStamp);
		this.tradeSetter.setVersion(version);
		this.tradeSetter.setStatus(status);
		this.tradeSetter.setInstitutionId(institutionId);

	}

	public Trade getTrade() {
		return this.tradeSetter.getTrade();
	}
}
