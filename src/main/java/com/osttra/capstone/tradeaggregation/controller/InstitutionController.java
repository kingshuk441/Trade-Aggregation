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

import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Institution;
import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.responsebody.InstitutionBody;
import com.osttra.capstone.tradeaggregation.service.InstitutionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
@Validated
public class InstitutionController {
	@Autowired
	private InstitutionService institutionService;

	@ApiOperation(value = "show all institutions")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@GetMapping("/institution")
	public CustomResponse<Institution> show() {
		return this.institutionService.getInstitutions();
	}

	@ApiOperation(value = "show institution by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@GetMapping("/institution/{id}")
	public CustomResponse<Institution> show2(@PathVariable int id) {
		return this.institutionService.getInstitution(id);
	}

	@ApiOperation(value = "show all parties of a institution")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@GetMapping("/institution/{id}/parties")
	public CustomResponse<Party> show3(@PathVariable int id) {
		return this.institutionService.getParties(id);
	}

	@ApiOperation(value = "add a party to a institution")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })

	@PostMapping("/institution/{id}/party/{partyId}")
	public CustomResponse<Institution> show4(@PathVariable int id, @PathVariable int partyId) {
		return this.institutionService.addParty(id, partyId);
	}

	@ApiOperation(value = "add new institution")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@PostMapping("/institution")
	public CustomResponse<Institution> show5(@Valid @RequestBody InstitutionBody body, BindingResult br) {
		return this.institutionService.addInstitution(body);
	}

	@ApiOperation(value = "get institution by name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@GetMapping("/institution/name/{name}")
	public CustomResponse<Institution> show6(@PathVariable String name) {
		return this.institutionService.getInstitutionByName(name);
	}

	@ApiOperation(value = "update institution by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@PutMapping("/institution/{id}")
	public CustomResponse<Institution> show7(@PathVariable int id, @RequestBody InstitutionBody body) {
		return this.institutionService.updateInstitution(id, body);
	}

	@ApiOperation(value = "delete institution by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@DeleteMapping("/institution/{id}")
	public CustomResponse<Institution> show8(@PathVariable int id) {
		return this.institutionService.deleteInstitution(id);
	}

	@ApiOperation(value = "Remove a party from institution")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Retrived List"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you where trying to reach is forbidden") })
	@PutMapping("/institution/{id}/{partyId}")
	public CustomResponse<Institution> show9(@PathVariable int id, @PathVariable int partyId) {
		return this.institutionService.removeParty(id, partyId);
	}

}
