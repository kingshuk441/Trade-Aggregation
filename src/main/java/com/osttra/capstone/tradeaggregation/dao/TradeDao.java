package com.osttra.capstone.tradeaggregation.dao;

import java.util.List;

import com.osttra.capstone.tradeaggregation.entity.Trade;

public interface TradeDao {
	public Trade saveTrade(Trade body);

	public List<Trade> getTrades();

	public void deleteTrade(int tradeId);

	public Trade getTrade(int id);

}
