package com.osttra.capstone.tradeaggregation.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;

@ApiModel
@Api(value = "Cancel Trade")
@Entity
@Table(name = "cancel_trade")
public class CancelTrade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cancel_id")
	private int cancelId;
	@Column(name = "notional_amount")
	private long notionalAmount;
	@Column(name = "creation_timestamp")
	private Date creationTimeStamp;
	@Column(name = "version_timestamp")
	private Date versionTimeStamp;
	@Column(name = "confirmation_timestamp")
	private Date confirmationTimeStamp;
	@Column(name = "trade_ref_num")
	private String tradeRefNum;

	@OneToOne
	@JoinColumn(name = "a_id")
	@JsonBackReference
	private Trade aggregatedTrade;

	public CancelTrade() {

	}

	public CancelTrade(int id, long notionalAmount, Date creationTimeStamp, Date versionTimeStamp,
			Date confirmationTimeStamp, Trade aggregatedTrade, String tradeRefNum) {
		super();
		this.cancelId = id;
		this.notionalAmount = notionalAmount;
		this.creationTimeStamp = creationTimeStamp;
		this.versionTimeStamp = versionTimeStamp;
		this.confirmationTimeStamp = confirmationTimeStamp;
		this.aggregatedTrade = aggregatedTrade;
		this.tradeRefNum = tradeRefNum;
	}

	public int getCancelId() {
		return cancelId;
	}

	public void setCancelId(int cancelId) {
		this.cancelId = cancelId;
	}

	public long getNotionalAmount() {
		return notionalAmount;
	}

	public void setNotionalAmount(long notionalAmount) {
		this.notionalAmount = notionalAmount;
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

	public Trade getAggregatedTrade() {
		return aggregatedTrade;
	}

	public void setAggregatedTrade(Trade aggregatedTrade) {
		this.aggregatedTrade = aggregatedTrade;
	}

	public String getTradeRefNum() {
		return tradeRefNum;
	}

	public void setTradeRefNum(String tradeRefNum) {
		this.tradeRefNum = tradeRefNum;
	}

	@Override
	public String toString() {
		return "CancelTrade [cancelId=" + cancelId + ", notionalAmount=" + notionalAmount + ", creationTimeStamp="
				+ creationTimeStamp + ", versionTimeStamp=" + versionTimeStamp + ", confirmationTimeStamp="
				+ confirmationTimeStamp + ", aggregatedTrade=" + aggregatedTrade.getTradeRefNum() + "]";
	}

}
