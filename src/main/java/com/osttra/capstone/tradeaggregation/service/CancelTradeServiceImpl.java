package com.osttra.capstone.tradeaggregation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osttra.capstone.tradeaggregation.customexception.NotFoundException;
import com.osttra.capstone.tradeaggregation.dao.CancelTradeDao;
import com.osttra.capstone.tradeaggregation.entity.CancelTrade;
import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Trade;

@Service
public class CancelTradeServiceImpl implements CancelTradeService {
	@Autowired
	private CancelTradeDao cancelTradeDao;

	@Override
	@Transactional
	public CustomResponse<CancelTrade> addCancelTrade(CancelTrade cancel) {
		CancelTrade c = this.cancelTradeDao.addCancelTrade(cancel);
		return new CustomResponse<>("cancel trades added successfully!", HttpStatus.ACCEPTED.value(), c);

	}

	@Override
	@Transactional
	public CustomResponse<CancelTrade> getCancelTrades() {
		List<CancelTrade> list = this.cancelTradeDao.getCancelTrades();
		return new CustomResponse<>("all cancel trades fetched successfully!", HttpStatus.ACCEPTED.value(), list);
	}

	@Override
	@Transactional
	public CustomResponse<CancelTrade> getCancelTrade(int id) {
		CancelTrade c = this.cancelTradeDao.getCancelTrade(id);
		if (c == null) {
			throw new NotFoundException("cancel trade with id " + id + " not found!");
		}
		return new CustomResponse<>("cancel trade fetched successfully!", HttpStatus.ACCEPTED.value(), c);
	}

	@Override
	@Transactional
	public CustomResponse<Trade> getAggregatedTrade(int id) {
		CustomResponse<CancelTrade> res = this.getCancelTrade(id);
		return new CustomResponse<>("aggregated trade fetched successfully!", HttpStatus.ACCEPTED.value(),
				res.getData().get(0).getAggregatedTrade());
	}

}
