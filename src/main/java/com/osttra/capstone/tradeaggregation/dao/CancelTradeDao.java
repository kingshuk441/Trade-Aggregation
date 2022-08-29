package com.osttra.capstone.tradeaggregation.dao;

import java.util.List;

import com.osttra.capstone.tradeaggregation.entity.CancelTrade;

public interface CancelTradeDao {
	public CancelTrade addCancelTrade(CancelTrade cancel);

	public List<CancelTrade> getCancelTrades();

	public CancelTrade getCancelTrade(int id);
}
