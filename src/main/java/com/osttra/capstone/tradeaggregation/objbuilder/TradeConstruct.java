package com.osttra.capstone.tradeaggregation.objbuilder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.osttra.capstone.tradeaggregation.entity.CancelTrade;
import com.osttra.capstone.tradeaggregation.entity.Trade;

public class TradeConstruct {
	Trade trade;

	public TradeConstruct() {
		this.trade = new Trade();
	}

	public void setTradeDate(LocalDate tradeDate) {
		if (tradeDate != null)
			this.trade.setTradeDate(tradeDate);
	}

	public void setTradeRefNum(String tradeRefNum) {
		if (tradeRefNum != null)
			this.trade.setTradeRefNum(tradeRefNum);
	}

	public void setPartyName(String partyName) {
		if (partyName != null)
			this.trade.setPartyName(partyName);
	}

	public void setCounterPartyName(String counterPartyName) {
		if (counterPartyName != null)
			this.trade.setCounterPartyName(counterPartyName);
	}

	public void setEffectiveDate(LocalDate effectiveDate) {
		if (effectiveDate != null)
			this.trade.setEffectiveDate(effectiveDate);
	}

	public void setPartyFullName(String partyFullName) {
		if (partyFullName != null)
			this.trade.setPartyFullName(partyFullName);
	}

	public void setCounterPartyFullName(String counterPartyFullName) {
		if (counterPartyFullName != null)
			this.trade.setCounterPartyFullName(counterPartyFullName);
	}

	public void setInstrumentId(String instrumentId) {
		if (instrumentId != null)
			this.trade.setInstrumentId(instrumentId);
	}

	public void setNotionalAmount(long notionalAmount) {
		if (notionalAmount != 0)
			this.trade.setNotionalAmount(notionalAmount);
	}

	public void setMaturityDate(LocalDate maturityDate) {
		if (maturityDate != null)
			this.trade.setMaturityDate(maturityDate);
	}

	public void setCurrency(String currency) {
		if (currency != null)
			this.trade.setCurrency(currency);
	}

	public void setSeller(String seller) {
		if (seller != null)
			this.trade.setSeller(seller);
	}

	public void setBuyer(String buyer) {
		if (buyer != null)
			this.trade.setBuyer(buyer);
	}

	public void setCreationTimeStamp(Date creationTimeStamp) {
		if (creationTimeStamp != null)
			this.trade.setCreationTimeStamp(creationTimeStamp);
	}

	public void setVersionTimeStamp(Date versionTimeStamp) {
		if (versionTimeStamp != null)
			this.trade.setVersionTimeStamp(versionTimeStamp);
	}

	public void setConfirmationTimeStamp(Date confirmationTimeStamp) {
		if (confirmationTimeStamp != null)
			this.trade.setConfirmationTimeStamp(confirmationTimeStamp);
	}

	public void setVersion(int version) {
		this.trade.setVersion(version);

	}

	public void setStatus(String status) {
		this.trade.setStatus(status);
	}

	public void setTradeId(int tradeId) {
		this.setTradeId(tradeId);
	}

	public void setAggregatedFrom(List<CancelTrade> aggregatedFrom) {
		if (aggregatedFrom != null)
			this.trade.setAggregatedFrom(aggregatedFrom);
	}

	public void setInstitutionId(int institutionId) {
		if (institutionId != 0)
			this.trade.setInstitutionId(institutionId);

	}

	public Trade getTrade() {
		return this.trade;
	}

}
