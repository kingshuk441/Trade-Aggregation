package com.osttra.capstone.tradeaggregation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.osttra.capstone.tradeaggregation.customexception.FoundException;
import com.osttra.capstone.tradeaggregation.customexception.NotFoundException;
import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Institution;
import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.repository.InstitutionRepository;
import com.osttra.capstone.tradeaggregation.repository.PartyRepository;
import com.osttra.capstone.tradeaggregation.responsebody.PartyBody;

@Service
public class PartyServiceImpl implements PartyService {
	@Autowired
	private PartyRepository partyRepository;
	@Autowired
	private InstitutionRepository institutionRepository;

	@Override
	public CustomResponse<Party> getParties() {
		List<Party> allParties = this.partyRepository.findAll();
		return new CustomResponse<>("all party fetched successfully!", HttpStatus.ACCEPTED.value(), allParties);
	}

	@Override
	public CustomResponse<Party> getParty(int partyId) {
		Party party = this.partyRepository.findById(partyId).get();
		if (party == null) {
			throw new NotFoundException("party with id " + partyId + " not found!");
		}
		return new CustomResponse<>("party fetched successfully!", HttpStatus.ACCEPTED.value(), party);
	}

	@Override
	public CustomResponse<Institution> getInstitution(int partyId) {
		Party party = this.partyRepository.findById(partyId).get();
		if (party == null) {
			throw new NotFoundException("party with id " + partyId + " not found!");
		}
		Institution institution = party.getInstitution();
		return new CustomResponse<>("Institution fetched successfully!", HttpStatus.ACCEPTED.value(), institution);
	}

	private void getPartyNameHelper(String s) {
		try {
			CustomResponse<Party> res = this.getPartyByName(s);
		} catch (Exception e) {
			System.out.println("not found");
			return;
		}
		throw new FoundException("Party already found!");
	}

	@Override
	public CustomResponse<Party> addParty(PartyBody party) {

		String partyName = party.getPartyName();
		this.getPartyNameHelper(partyName);
		Party newParty = new Party();
		newParty.setPartyName(partyName);
		newParty.setPartyFullName(party.getPartyFullName());
		int institutionId = party.getInstitution();
		if (institutionId != 0) {
			Institution institution = this.institutionRepository.findById(institutionId).get();
			if (institution == null) {
				throw new NotFoundException("Institution with name " + partyName + " not found!");
			}
			newParty.setInstitution(institution);
		}
		newParty = this.partyRepository.save(newParty);
		return new CustomResponse<>("Party Added successfully!", HttpStatus.ACCEPTED.value(), newParty);
	}

	@Override
	public CustomResponse<Party> getPartyByName(String name) {
		Party party = this.partyRepository.findByPartyName(name);
		if (party == null) {
			throw new NotFoundException("Party with name " + name + " not found!");
		}

		return new CustomResponse<>("Party fetched successfully!", HttpStatus.ACCEPTED.value(), party);
	}

	@Override
	public CustomResponse<Party> updateParty(int partyId, PartyBody partyDetails) {
		Party party = this.partyRepository.findById(partyId).get();
		if (party == null) {
			throw new NotFoundException("party with id " + partyId + " not found!");
		}
		String name = partyDetails.getPartyName();
		int institutionId = partyDetails.getInstitution();

		if (institutionId != 0 && party.getInstitution() == null) {
			Institution i = this.institutionRepository.findById(institutionId).get();
			if (i == null) {
				throw new NotFoundException("Institution with id " + partyId + " not found!");
			}
			party.setInstitution(i);
		}
		if (name != null) {
			party.setPartyName(name);
		}
		if (partyDetails.getPartyFullName() != null) {
		}
		party = this.partyRepository.save(party);
		return new CustomResponse<>("Party Updated successfully!", HttpStatus.ACCEPTED.value(), party);
	}

	@Override
	public CustomResponse<Party> deleteParty(int partyId) {
		Party party = this.partyRepository.findById(partyId).get();
		if (party == null) {
			throw new NotFoundException("party with id " + partyId + " not found!");
		}
		this.partyRepository.deleteById(partyId);
		return new CustomResponse<>("Party Deleted successfully!", HttpStatus.ACCEPTED.value(), party);
	}
}
