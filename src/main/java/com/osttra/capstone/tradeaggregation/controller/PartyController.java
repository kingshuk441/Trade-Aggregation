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
import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.responsebody.PartyBody;
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

	// show Institution
	@GetMapping("/party/{id}/institution")
	public CustomResponse<Institution> show3(@PathVariable int id) {
		return this.partyService.getInstitution(id);
	}

	// add a party
	@PostMapping("/party")
	public CustomResponse<Party> show4(@RequestBody PartyBody party) {
		return this.partyService.addParty(party);
	}

	// get party by name
	@GetMapping("/party/name/{name}")
	public CustomResponse<Party> show4(@PathVariable String name) {
		return this.partyService.getPartyByName(name);
	}

	// update a party
	@PutMapping("/party/{id}")
	public CustomResponse<Party> show5(@PathVariable int id, @RequestBody PartyBody party) {
		return this.partyService.updateParty(id, party);
	}

	// delete party
	@DeleteMapping("/party/{id}")
	public CustomResponse<Party> show6(@PathVariable int id) {
		return this.partyService.deleteParty(id);
	}
}
