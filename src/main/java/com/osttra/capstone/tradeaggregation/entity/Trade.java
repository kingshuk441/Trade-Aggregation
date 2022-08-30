package com.osttra.capstone.tradeaggregation.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.osttra.capstone.tradeaggregation.responsebody.TradeBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;

@ApiModel
@Api("Trade")
@Entity
@Table(name = "trade")
public class Trade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trade_id")
	private int tradeId;
	@Column(name = "trade_ref_num")
	private String tradeRefNum;
	@Column(name = "party_name")
	private String partyName;
	@Column(name = "party_fullname")
	@Size(max = 200, message = "party full name length cant be more than 200")
	@NotNull(message = "party full name is required")
	private String partyFullName;
	@Column(name = "counter_party_name")
	@Size(max = 200, message = "party full name length cant be more than 200")
	private String counterPartyName;
	@Column(name = "counter_party_fullname")
	@Size(max = 200, message = "counter party full name length cant be more than 200")
	private String counterPartyFullName;
	@Column(name = "trade_date")
	private LocalDate tradeDate;
	@Column(name = "effective_date")
	private LocalDate effectiveDate;
	@Column(name = "instrument_id")
	private String instrumentId;
	private long notionalAmount;
	@Column(name = "maturity_date")
	private LocalDate maturityDate;
	@Column(name = "currency")
	private String currency;
	@Column(name = "seller")
	private String seller;
	@Column(name = "buyer")
	private String buyer;
	@Column(name = "creation_timestamp")
	@NotNull(message = "creation timestamp is required")
	private Date creationTimeStamp;
	@Column(name = "version_timestamp")
	@NotNull(message = "version timestamp is required")
	private Date versionTimeStamp;
	@Column(name = "confirmation_timestamp")
	private Date confirmationTimeStamp;
	@Column(name = "version")
	private int version;
	@Column(name = "status")
	@Size(max = 50, message = "status length cant be more than 50")
	@NotNull(message = "status is required")
	private String status;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "a_id")
	@JsonManagedReference
	private List<CancelTrade> aggregatedFrom;

	@Column(name = "institution_id")
	@Min(value = 1, message = "institution id is required")
	private int institutionId;

	public Trade() {
	}

	public Trade(TradeBody t,
			@Size(max = 200, message = "party full name length cant be more than 200") String partyFullName,
			@Size(max = 200, message = "counter party full name length cant be more than 200") String counterPartyFullName,
			@NotNull(message = "creation timestamp is required") Date creationTimeStamp,
			@NotNull(message = "version timestamp is required") Date versionTimeStamp, Date confirmationTimeStamp,
			int version,
			@Size(max = 50, message = "status length cant be more than 50") @NotNull(message = "status is required") String status,
			@Min(value = 1, message = "institution id is required") int institutionId) {
		this.tradeRefNum = t.getTradeRefNum();
		this.partyName = t.getPartyName();
		this.counterPartyName = t.getCounterPartyName();
		this.partyFullName = partyFullName;
		this.counterPartyFullName = counterPartyFullName;
		this.tradeDate = t.getTradeDate();
		this.effectiveDate = t.getEffectiveDate();
		this.instrumentId = t.getInstrumentId();
		this.notionalAmount = t.getNotionalAmount();
		this.maturityDate = t.getMaturityDate();
		this.currency = t.getCurrency();
		this.seller = t.getCurrency();
		this.buyer = t.getBuyer();
		this.creationTimeStamp = creationTimeStamp;
		this.versionTimeStamp = versionTimeStamp;
		this.confirmationTimeStamp = confirmationTimeStamp;
		this.version = version;
		this.status = status;
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

	public String getPartyFullName() {
		return partyFullName;
	}

	public void setPartyFullName(String partyFullName) {
		this.partyFullName = partyFullName;
	}

	public String getCounterPartyName() {
		return counterPartyName;
	}

	public void setCounterPartyName(String counterParty) {
		this.counterPartyName = counterParty;
	}

	public String getCounterPartyFullName() {
		return counterPartyFullName;
	}

	public void setCounterPartyFullName(String counterPartyFullName) {
		this.counterPartyFullName = counterPartyFullName;
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

	public Date getCreationTimeStamp() {
		return creationTimeStamp;
	}

	public void setCreationTimeStamp(Date creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTradeId() {
		return tradeId;
	}

	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}

	public List<CancelTrade> getAggregatedFrom() {
		return aggregatedFrom;
	}

	public void setAggregatedFrom(List<CancelTrade> aggregatedFrom) {
		this.aggregatedFrom = aggregatedFrom;
	}

	public int getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(int institutionId) {
		this.institutionId = institutionId;
	}

	public void addCancelTrades(CancelTrade c) {
		if (this.aggregatedFrom == null) {
			this.aggregatedFrom = new ArrayList<>();
		}
		this.aggregatedFrom.add(c);
	}

	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", tradeRefNum=" + tradeRefNum + ", partyName=" + partyName
				+ ", partyFullName=" + partyFullName + ", counterPartyName=" + counterPartyName
				+ ", counterPartyFullName=" + counterPartyFullName + ", tradeDate=" + tradeDate + ", effectiveDate="
				+ effectiveDate + ", instrumentId=" + instrumentId + ", notionalAmount=" + notionalAmount
				+ ", maturityDate=" + maturityDate + ", currency=" + currency + ", seller=" + seller + ", buyer="
				+ buyer + ", creationTimeStamp=" + creationTimeStamp + ", versionTimeStamp=" + versionTimeStamp
				+ ", confirmationTimeStamp=" + confirmationTimeStamp + ", version=" + version + ", status=" + status
				+ ", aggregatedFrom=" + aggregatedFrom + ", institutionId=" + institutionId + "]";
	}

}
