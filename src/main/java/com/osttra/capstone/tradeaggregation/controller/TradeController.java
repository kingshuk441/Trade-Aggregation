package com.osttra.capstone.tradeaggregation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osttra.capstone.tradeaggregation.entity.CancelTrade;
import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Trade;
import com.osttra.capstone.tradeaggregation.responsebody.TradeBody;
import com.osttra.capstone.tradeaggregation.responsebody.TradeUpdateBody;
import com.osttra.capstone.tradeaggregation.service.TradeService;

@RestController
@RequestMapping("/api")
@Validated
public class TradeController {
	@Autowired
	private TradeService tradeService;

	// add trade
	@PostMapping("/trade")
	public CustomResponse<Trade> show(@Valid @RequestBody TradeBody body, BindingResult br) {
		return this.tradeService.addTrade(body);
	}

	// get all trades
	@GetMapping("/trades")
	public CustomResponse<Trade> show2() {
		return this.tradeService.getTrades();
	}

	// get trade by id
	@GetMapping("/trade/{id}")
	public CustomResponse<Trade> show3(@PathVariable int id) {
		return this.tradeService.getTrade(id);
	}

	// get cancel trade by id
	@GetMapping("/trade/{id}/cancel")
	public CustomResponse<CancelTrade> show4(@PathVariable int id) {
		return this.tradeService.getCancelTrades(id);
	}

	// update trade by id
	@PutMapping("/trade/{id}")
	public CustomResponse<Trade> show5(@PathVariable int id, @Valid @RequestBody TradeUpdateBody body,
			BindingResult br) {
		System.out.println(body);
		return this.tradeService.updateTrade(id, body);
	}
}
