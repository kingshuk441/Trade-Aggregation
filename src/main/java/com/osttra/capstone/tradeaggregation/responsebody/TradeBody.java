package com.osttra.capstone.tradeaggregation.responsebody;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.osttra.capstone.tradeaggregation.validation.PartyName;
import com.osttra.capstone.tradeaggregation.validation.PartySame;

public class TradeBody {
	@NotNull(message = "trade ref num is required")
	private String tradeRefNum;
	@PartyName(message = "party name (${validatedValue}) must be valid")
	@PartySame(message = "party name and counter party name cant be same")
	@NotNull(message = "party name  is required")
	@Size(max = 20, message = "party name length cant be more than 20")
	private String partyName;
	@PartyName(message = "counter party (${validatedValue}) name must be valid")
	@PartySame(message = "party name and counter party name cant be same")
	@NotNull(message = "counter party name is required")
	@Size(max = 20, message = "counter party name length cant be more than 20")
	private String counterPartyName;
	@NotNull(message = "trade date is required")
	private LocalDate tradeDate;
	@NotNull(message = "effective date is required")
	private LocalDate effectiveDate;
	@NotNull(message = "instrument id is required")
	@Size(max = 40, message = "instrument id length cant be more than 40")
	private String instrumentId;
	@Min(value = 1, message = "notional amount cant be 0")
	private long notionalAmount;
	@NotNull(message = "maturity date is required")
	private LocalDate maturityDate;
	@NotNull(message = "currency is required")
	@Size(max = 3, message = "currency length cant be more than 3")
	private String currency;
	@Size(max = 20, message = "seller length cant be more than 20")
	@NotNull(message = "seller is required")
	private String seller;
	@Size(max = 20, message = "buyer length cant be more than 20")
	@NotNull(message = "buyer is required")
	private String buyer;

	public TradeBody(@NotNull(message = "trade ref num is required") String tradeRefNum, String partyName,
			String counterPartyName, @NotNull(message = "trade date is required") LocalDate tradeDate,
			@NotNull(message = "effective date is required") LocalDate effectiveDate,
			@NotNull(message = "instrument id is required") @Size(max = 40, message = "instrument id length cant be more than 40") String instrumentId,
			@Min(value = 1, message = "notional amount cant be 0") long notionalAmount,
			@NotNull(message = "maturity date is required") LocalDate maturityDate,
			@NotNull(message = "currency is required") @Size(max = 3, message = "currency length cant be more than 3") String currency,
			@Size(max = 20, message = "seller length cant be more than 20") @NotNull(message = "seller is required") String seller,
			@Size(max = 20, message = "buyer length cant be more than 20") @NotNull(message = "buyer is required") String buyer) {
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
	}

	public LocalDate getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(LocalDate tradeDate) {
		this.tradeDate = tradeDate;
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

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentOd) {
		this.instrumentId = instrumentOd;
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

	@Override
	public String toString() {
		return "TradeBody [tradeRefNum=" + tradeRefNum + ", partyName=" + partyName + ", counterPartyName="
				+ counterPartyName + ", effectiveDate=" + effectiveDate + ", instrumentId=" + instrumentId
				+ ", notionalAmount=" + notionalAmount + ", maturityDate=" + maturityDate + ", currency=" + currency
				+ ", seller=" + seller + ", buyer=" + buyer + "]";
	}

}
