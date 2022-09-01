package com.osttra.capstone.tradeaggregation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osttra.capstone.tradeaggregation.entity.CancelTrade;
import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Trade;
import com.osttra.capstone.tradeaggregation.service.CancelTradeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class CancelTradeController {
	@Autowired
	private CancelTradeService cancelTradeService;

	@ApiOperation(value = "get all cancel trades")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@GetMapping("/cancels")
	public CustomResponse<CancelTrade> show2() {
		return this.cancelTradeService.getCancelTrades();
	}

	@ApiOperation(value = "get cancel trade by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@GetMapping("/cancel/{id}")
	public CustomResponse<CancelTrade> show3(@PathVariable int id) {
		return this.cancelTradeService.getCancelTrade(id);
	}

	@ApiOperation(value = "get aggregated trade of cancel trade")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@GetMapping("/cancel/{id}/aggr")
	public CustomResponse<Trade> show4(@PathVariable int id) {
		return this.cancelTradeService.getAggregatedTrade(id);
	}
}
