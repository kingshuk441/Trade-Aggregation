package com.osttra.capstone.tradeaggregation.service;

import com.osttra.capstone.tradeaggregation.entity.CancelTrade;
import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Trade;
import com.osttra.capstone.tradeaggregation.responsebody.TradeBody;
import com.osttra.capstone.tradeaggregation.responsebody.TradeUpdateBody;

public interface TradeService {
	public CustomResponse<Trade> addTrade(TradeBody body);

	public CustomResponse<Trade> getTrades();

	public CustomResponse<Trade> getTrade(int id);

	public CustomResponse<CancelTrade> getCancelTrades(int id);

	public CustomResponse<Trade> updateTrade(int id, TradeUpdateBody body);
}
