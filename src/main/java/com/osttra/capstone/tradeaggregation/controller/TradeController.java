package com.osttra.capstone.tradeaggregation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
@Validated
public class TradeController {
	@Autowired
	private TradeService tradeService;

	@ApiOperation(value = "add new trade (API - 1)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@PostMapping("/trade")
	public CustomResponse<Trade> show(@Valid @RequestBody TradeBody body, BindingResult br) {
		return this.tradeService.addTrade(body);
	}

	@ApiOperation(value = "get all trades")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@GetMapping("/trades")
	public CustomResponse<Trade> show2() {
		return this.tradeService.getTrades();
	}

	@ApiOperation(value = "get trade by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@GetMapping("/trade/{id}")
	public CustomResponse<Trade> show3(@PathVariable int id) {
		return this.tradeService.getTrade(id);
	}

	@ApiOperation(value = " get cancel trades of aggregated trade by id (API - 3)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@GetMapping("/trade/{id}/cancel")
	public CustomResponse<CancelTrade> show4(@PathVariable int id) {
		return this.tradeService.getCancelTrades(id);
	}

	@ApiOperation(value = "update trade by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@PutMapping("/trade/{id}")
	public CustomResponse<Trade> show5(@PathVariable int id, @Valid @RequestBody TradeUpdateBody body,
			BindingResult br) {
		return this.tradeService.updateTrade(id, body);
	}

	@ApiOperation(value = "find trades by party name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })

	@GetMapping("/trade/party/{partyName}")
	public CustomResponse<Trade> show6(@PathVariable String partyName) {
		return this.tradeService.findByPartyName(partyName);
	}

	@ApiOperation(value = "find trades by institution name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })

	@GetMapping("/trade/institution/{institutionName}")
	public CustomResponse<Trade> show7(@PathVariable String institutionName) {
		return this.tradeService.findByInstitutionName(institutionName);
	}

	@ApiOperation(value = "Search trade by trn and party Name (API2 - I)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@GetMapping("/trade/search/1/{trn}/{partyName}")
	public CustomResponse<Trade> show8(@PathVariable String trn, @PathVariable String partyName) {
		return this.tradeService.findByTrnParty(trn, partyName);
	}

	@ApiOperation(value = "Search all trades by status and party Name (API2 - II)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@GetMapping("/trade/search/2/{partyName}/{status}")
	public CustomResponse<Trade> show9(@PathVariable String partyName, @PathVariable String status) {
		return this.tradeService.findByPartyStatus(partyName, status);
	}

	@ApiOperation(value = "delete trade by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@DeleteMapping("/trade/{id}")
	public CustomResponse<Trade> show10(@PathVariable int id) {
		return this.tradeService.deleteTrade(id);
	}

}
