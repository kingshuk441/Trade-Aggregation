package com.osttra.capstone.tradeaggregation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osttra.capstone.tradeaggregation.entity.CancelTrade;
import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Trade;
import com.osttra.capstone.tradeaggregation.service.CancelTradeService;

@RestController
@RequestMapping("/api")
public class CancelTradeController {
	@Autowired
	private CancelTradeService cancelTradeService;

	@PostMapping("/cancel")
	public CustomResponse<CancelTrade> show(CancelTrade c) {
		return this.cancelTradeService.addCancelTrade(c);
	}

	@GetMapping("/cancels")
	public CustomResponse<CancelTrade> show2() {
		return this.cancelTradeService.getCancelTrades();
	}

	@GetMapping("/cancel/{id}")
	public CustomResponse<CancelTrade> show3(@PathVariable int id) {
		return this.cancelTradeService.getCancelTrade(id);
	}

	@GetMapping("/cancel/{id}/aggr")
	public CustomResponse<Trade> show4(@PathVariable int id) {
		return this.cancelTradeService.getAggregatedTrade(id);
	}
}
