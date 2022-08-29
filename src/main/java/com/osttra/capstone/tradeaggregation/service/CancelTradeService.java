package com.osttra.capstone.tradeaggregation.service;

import com.osttra.capstone.tradeaggregation.entity.CancelTrade;
import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Trade;

public interface CancelTradeService {
	public CustomResponse<CancelTrade> addCancelTrade(CancelTrade cancel);

	public CustomResponse<CancelTrade> getCancelTrades();

	public CustomResponse<CancelTrade> getCancelTrade(int id);

	public CustomResponse<Trade> getAggregatedTrade(int id);
}
