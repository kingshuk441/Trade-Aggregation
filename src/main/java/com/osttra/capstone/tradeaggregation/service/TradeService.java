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

	public CustomResponse<Trade> findByPartyName(String partyName);

	public CustomResponse<Trade> findByInstitutionName(String institutionName);

	public CustomResponse<Trade> findByTrnParty(String trn, String partyName);

	CustomResponse<Trade> findByPartyStatus(String partyName, String status);

	public CustomResponse<Trade> deleteTrade(int id);

	CustomResponse<Trade> getAllAggregatedTrades();

	CustomResponse<Trade> getAllUnconfirmedTrades();
}
