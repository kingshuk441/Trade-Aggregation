package com.osttra.capstone.tradeaggregation.responsebody;

import java.time.LocalDate;
import java.util.Date;

import com.osttra.capstone.tradeaggregation.validation.PartyName;
import com.osttra.capstone.tradeaggregation.validation.PartySame;

public class TradeUpdateBody {
	// not used
//	private int tradeId;
	private String tradeRefNum;
	@PartyName
	@PartySame
	private String partyName;
	// not used
//	private String partyFullName;
	@PartyName
	@PartySame
	private String counterPartyName;
	// not used
//	private String counterPartyFullName;
	private LocalDate tradeDate;
	private LocalDate effectiveDate;
	private String instrumentId;
	private long notionalAmount;
	private LocalDate maturityDate;
	private String currency;
	private String seller;
	private String buyer;
	// not used
//	private Date creationTimeStamp;
	private Date versionTimeStamp;
	private Date confirmationTimeStamp;
	private int version;
	// not used
//	private String status;
	private int institutionId;
	// not used
//	private List<CancelTrade> aggregatedFrom;

	public TradeUpdateBody() {

	}

	public TradeUpdateBody(String tradeRefNum, String partyName, String counterPartyName, LocalDate tradeDate,
			LocalDate effectiveDate, String instrumentId, long notionalAmount, LocalDate maturityDate, String currency,
			String seller, String buyer, Date versionTimeStamp, Date confirmationTimeStamp, int version,
			int institutionId) {
		super();
		this.tradeRefNum = tradeRefNum;
		this.partyName = partyName;
		this.counterPartyName = counterPartyName;
		this.tradeDate = tradeDate;
		this.effectiveDate = effectiveDate;
		this.instrumentId = instrumentId;
		this.notionalAmount = notionalAmount;
		this.maturityDate = maturityDate;
		this.currency = currency;
		this.seller = seller;
		this.buyer = buyer;
		this.versionTimeStamp = versionTimeStamp;
		this.confirmationTimeStamp = confirmationTimeStamp;
		this.version = version;
		this.institutionId = institutionId;
	}

	public String getTradeRefNum() {
		return tradeRefNum;
	}

	public void setTradeRefNum(String tradeRefNum) {
		this.tradeRefNum = tradeRefNum;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getCounterPartyName() {
		return counterPartyName;
	}

	public void setCounterPartyName(String counterPartyName) {
		this.counterPartyName = counterPartyName;
	}

	public LocalDate getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(LocalDate tradeDate) {
		this.tradeDate = tradeDate;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public long getNotionalAmount() {
		return notionalAmount;
	}

	public void setNotionalAmount(long notionalAmount) {
		this.notionalAmount = notionalAmount;
	}

	public LocalDate getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(LocalDate maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public Date getVersionTimeStamp() {
		return versionTimeStamp;
	}

	public void setVersionTimeStamp(Date versionTimeStamp) {
		this.versionTimeStamp = versionTimeStamp;
	}

	public Date getConfirmationTimeStamp() {
		return confirmationTimeStamp;
	}

	public void setConfirmationTimeStamp(Date confirmationTimeStamp) {
		this.confirmationTimeStamp = confirmationTimeStamp;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(int institutionId) {
		this.institutionId = institutionId;
	}

}
