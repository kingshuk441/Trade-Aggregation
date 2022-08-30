package com.osttra.capstone.tradeaggregation.dao;

import java.util.List;

import com.osttra.capstone.tradeaggregation.entity.Trade;

public interface TradeDao {
	public Trade saveTrade(Trade body);

	public List<Trade> getTrades();

	public void deleteTrade(int tradeId);

	public Trade getTrade(int id);

	public Trade updateTrade(Trade newTrade);

	public List<Trade> getTradesByPartyName(String partyName);

	public List<Trade> getTradesByInstitutionId(int id);

}
