package com.osttra.capstone.tradeaggregation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Institution;
import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.service.InstituionService;

@RestController
@RequestMapping("/api")
public class InstitutionController {
	@Autowired
	private InstituionService instituionService;

	@GetMapping("/institution")

	public CustomResponse<Institution> show() {
		return this.instituionService.getInstitutions();
	}

	@GetMapping("/institution/{id}")
	public CustomResponse<Institution> show2(@PathVariable int id) {
		return this.instituionService.getInstitution(id);

	}

	@GetMapping("/institution/{id}/parties")
	public CustomResponse<Party> show3(@PathVariable int id) {
		return this.instituionService.getParties(id);
	}

	@PostMapping("/institution/{id}/party/{partyId}")
	public CustomResponse<Institution> show4(@PathVariable int id, @PathVariable int partyId) {
		return this.instituionService.addParty(id, partyId);

	}
}
