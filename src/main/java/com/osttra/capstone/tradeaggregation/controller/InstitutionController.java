package com.osttra.capstone.tradeaggregation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Institution;
import com.osttra.capstone.tradeaggregation.entity.InstitutionBody;
import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.service.InstituionService;

@RestController
@RequestMapping("/api")
public class InstitutionController {
	@Autowired
	private InstituionService instituionService;

	// show all institutions
	@GetMapping("/institution")
	public CustomResponse<Institution> show() {
		return this.instituionService.getInstitutions();
	}

	// show institution by id
	@GetMapping("/institution/{id}")
	public CustomResponse<Institution> show2(@PathVariable int id) {
		return this.instituionService.getInstitution(id);
	}

	// show all parties
	@GetMapping("/institution/{id}/parties")
	public CustomResponse<Party> show3(@PathVariable int id) {
		return this.instituionService.getParties(id);
	}

	// add a party
	@PostMapping("/institution/{id}/party/{partyId}")
	public CustomResponse<Institution> show4(@PathVariable int id, @PathVariable int partyId) {
		return this.instituionService.addParty(id, partyId);
	}

	// add institution
	@PostMapping("/institution")
	public CustomResponse<Institution> show5(@RequestBody Institution body) {
		return this.instituionService.addInstituion(body);
	}

	// get institution by name
	@GetMapping("/institution/name/{name}")
	public CustomResponse<Institution> show6(@PathVariable String name) {
		return this.instituionService.getInstitutionByName(name);
	}

	// update institution
	@PutMapping("/institution/{id}")
	public CustomResponse<Institution> show7(@PathVariable int id, @RequestBody InstitutionBody body) {
		return this.instituionService.updateInstitution(id, body);
	}

	// delete institution
	@DeleteMapping("/institution/{id}")
	public CustomResponse<Institution> show8(@PathVariable int id) {
		return this.instituionService.deleteInstitution(id);
	}

}
