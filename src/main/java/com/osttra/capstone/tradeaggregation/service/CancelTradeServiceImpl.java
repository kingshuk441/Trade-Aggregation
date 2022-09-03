package com.osttra.capstone.tradeaggregation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osttra.capstone.tradeaggregation.customexception.NotFoundException;
import com.osttra.capstone.tradeaggregation.entity.CancelTrade;
import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Trade;
import com.osttra.capstone.tradeaggregation.repository.CancelRepository;

@Service
public class CancelTradeServiceImpl implements CancelTradeService {
	@Autowired
	private CancelRepository cancelRepository;

	@Override
	public CustomResponse<CancelTrade> addCancelTrade(CancelTrade cancel) {
		CancelTrade c = this.cancelRepository.save(cancel);
		return new CustomResponse<>("cancel trades added successfully!", HttpStatus.ACCEPTED.value(), c);

	}

	@Override
	public CustomResponse<CancelTrade> getCancelTrades() {
		List<CancelTrade> list = this.cancelRepository.findAll();
		return new CustomResponse<>("all cancel trades fetched successfully!", HttpStatus.ACCEPTED.value(), list);
	}

	@Override
	public CustomResponse<CancelTrade> getCancelTrade(int id) {
		Optional<CancelTrade> c = this.cancelRepository.findById(id);
		if (c.isEmpty()) {
			throw new NotFoundException("cancel trade with id " + id + " not found!");
		}
		return new CustomResponse<>("cancel trade fetched successfully!", HttpStatus.ACCEPTED.value(), c.get());
	}

	@Override
	@Transactional
	public CustomResponse<Trade> getAggregatedTrade(int id) {
		CustomResponse<CancelTrade> res = this.getCancelTrade(id);
		return new CustomResponse<>("aggregated trade fetched successfully!", HttpStatus.ACCEPTED.value(),
				res.getData().get(0).getAggregatedTrade());
	}

}
