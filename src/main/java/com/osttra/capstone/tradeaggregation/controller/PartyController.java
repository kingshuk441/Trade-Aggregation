package com.osttra.capstone.tradeaggregation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.service.PartyService;

@RestController
@RequestMapping("/api")
public class PartyController {
	@Autowired
	private PartyService partyService;

	// show all parties
	@GetMapping("/parties")
	public CustomResponse<Party> show() {
		return this.partyService.getParties();
	}

	// show party by id
	@GetMapping("/party/{id}")
	public CustomResponse<Party> show2(@PathVariable int id) {
		return this.partyService.getParty(id);

	}
}
