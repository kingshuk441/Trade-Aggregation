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

//	public TradeBuilder(Trade t, TradeUpdateBody body, PartyDao partyDao) {
//		// tradeId,creTimeStamp,status,confirmTS
//		t.setTradeRefNum(body.getTradeRefNum() == null ? t.getTradeRefNum() : body.getTradeRefNum());
//		if (body.getPartyName() != null) {
//			t.setPartyName(body.getPartyName());
//			Party p = partyDao.getPartyByName(body.getPartyName());
//			t.setPartyFullName(p.getPartyFullName());
//			t.setInstitutionId(p.getInstitution().getInstitutionId());
//		}
//		if (body.getCounterPartyName() != null) {
//			t.setCounterPartyName(body.getCounterPartyName());
//			Party p = partyDao.getPartyByName(body.getCounterPartyName());
//			t.setCounterPartyFullName(p.getPartyFullName());
//		}
//		t.setTradeDate(body.getTradeDate() == null ? t.getTradeDate() : body.getTradeDate());
//		t.setEffectiveDate(body.getEffectiveDate() == null ? t.getEffectiveDate() : body.getEffectiveDate());
//		t.setInstrumentId(body.getInstrumentId() == null ? t.getInstrumentId() : body.getInstrumentId());
//		t.setNotionalAmount(body.getNotionalAmount() == 0 ? t.getNotionalAmount() : body.getNotionalAmount());
//		t.setMaturityDate(body.getMaturityDate() == null ? t.getMaturityDate() : body.getMaturityDate());
//		t.setCurrency(body.getCurrency() == null ? t.getCurrency() : body.getBuyer());
//		t.setSeller(body.getSeller() == null ? t.getSeller() : body.getSeller());
//		t.setBuyer(body.getBuyer() == null ? t.getBuyer() : body.getBuyer());
//		t.setVersionTimeStamp(new Date());
//		t.setVersion(t.getVersion() + 1);
//	}

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
			// TODO party and counter party cant be same
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

	public void constructNewTrade(String partyFullName, String counterPartyFullName, int institutionId,
			Date creationDate) {
		this.partyFullName = partyFullName;
		this.counterPartyFullName = counterPartyFullName;
		this.creationTimeStamp = creationDate;
		this.versionTimeStamp = creationDate;
		this.version = 0;
		this.status = "UF";
		this.institutionId = institutionId;
		this.fillValues();
	}

	public void mergeNewTrade(String partyFullName, String counterPartyFullName, int institutionId,
			Date updatedTimeStamp, String trn, long notionalAmount, Date cd) {
		this.partyFullName = partyFullName;
		this.counterPartyFullName = counterPartyFullName;
		this.creationTimeStamp = cd;
		this.versionTimeStamp = updatedTimeStamp;
		this.version = 0;
		this.status = "AGG";
		this.institutionId = institutionId;
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
