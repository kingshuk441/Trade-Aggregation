package com.osttra.capstone.tradeaggregation.service;

import java.util.ArrayList;
import java.util.HashSet;
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
import com.osttra.capstone.tradeaggregation.responsebody.InstitutionBody;

@Service
public class InstitutionServiceImpl implements InstitutionService {
	@Autowired
	private InstitutionRepository institutionRepository;
	@Autowired
	private PartyRepository partyRepository;

	@Override
	public CustomResponse<Institution> getInstitutions() {
		List<Institution> allInstitutions = this.institutionRepository.findAll();
		return new CustomResponse<>("all institution fetched successfully!", HttpStatus.ACCEPTED.value(),
				allInstitutions);
	}

	@Override
	public CustomResponse<Institution> getInstitution(int instiId) {
		Institution institution = this.institutionRepository.findById(instiId).get();
		if (institution == null) {
			throw new NotFoundException("Institution with id " + instiId + " not found!");
		}
		return new CustomResponse<>("institution of id " + instiId + " fetched successfully!",
				HttpStatus.ACCEPTED.value(), institution);
	}

	@Override
	public CustomResponse<Party> getParties(int instiId) {
		Institution institution = this.institutionRepository.findById(instiId).get();
		if (institution == null) {
			throw new NotFoundException("Institution with id " + instiId + " not found!");
		}
		List<Party> allParties = institution.getAllParties();
		return new CustomResponse<>("parties fetched successfully!", HttpStatus.ACCEPTED.value(), allParties);
	}

	public Institution addParty(int instiId, Party partyToAdd) {

		Institution institution = this.institutionRepository.findById(instiId).get();
		for (Party party : institution.getAllParties()) {
			if (party.getPartyId() == partyToAdd.getPartyId()) {
				return null;
			}
		}

		institution.addParty(partyToAdd);
		return institution;

	}

	@Override
	public CustomResponse<Institution> addParty(int instiId, int partyId) {
		Party party = this.partyRepository.findById(partyId).get();
		if (party == null) {
			throw new NotFoundException("party with id " + instiId + " not found!");
		}
		Institution institution = this.addParty(instiId, party);
		if (institution == null) {
			throw new RuntimeException("already added!");
		}
		this.institutionRepository.save(institution);
		return new CustomResponse<>("party added successfully!", HttpStatus.ACCEPTED.value(), institution);

	}

	@Override
	public CustomResponse<Institution> addInstitution(InstitutionBody body) {
		String intitutionName = body.getInstitutionName();
		this.getInstituteNameHelper(intitutionName);
		Institution institution = new Institution();
		institution.setInstitutionName(intitutionName);
		HashSet<Integer> set = new HashSet<>();
		if (body.getParties() != null) {
			for (int partyId : body.getParties()) {
				if (set.contains(partyId) == false) {
					set.add(partyId);
					Party party = this.partyRepository.findById(partyId).get();
					institution.addParty(party);
				}
			}
		}
		institution = this.institutionRepository.save(institution);
		return new CustomResponse<>("institution added successfully!", HttpStatus.ACCEPTED.value(), institution);
	}

	// only for exception handling
	private void getInstituteNameHelper(String s) {
		try {
			CustomResponse<Institution> res = this.getInstitutionByName(s);
		} catch (Exception e) {
			System.out.println("not found");
			return;
		}
		throw new FoundException("institution already found!");
	}

	@Override
	public CustomResponse<Institution> getInstitutionByName(String name) {
		Institution institution = this.institutionRepository.findByInstitutionName(name);
		if (institution == null) {
			throw new NotFoundException("Institution with name " + name + " not found!");
		}

		return new CustomResponse<>("Institute fetched successfully!", HttpStatus.ACCEPTED.value(), institution);
	}

	@Override
	public CustomResponse<Institution> updateInstitution(int instiId, InstitutionBody body) {
		Institution institution = this.institutionRepository.findById(instiId).get();
		if (institution == null) {
			throw new NotFoundException("Institution with id " + instiId + " not found!");
		}
		if (body.getInstitutionName() != null) {
			institution.setInstitutionName(body.getInstitutionName());
		}
		if (body.getParties() != null) {
			HashSet<Integer> alreadyPresent = new HashSet<>();
			List<Party> addParties = new ArrayList<>();
			for (int partyId : body.getParties()) {
				Party party = this.partyRepository.findById(partyId).get();
				addParties.add(party);
				alreadyPresent.add(partyId);
			}
			for (Party party : institution.getAllParties()) {
				if (alreadyPresent.contains(party.getPartyId()) == false) {
					addParties.add(party);
					alreadyPresent.add(party.getPartyId());
				}
			}
			institution.setAllParties(addParties);
		}
		Institution updatedInstitution = this.institutionRepository.save(institution);
		return new CustomResponse<>("Institute updated successfully!", HttpStatus.ACCEPTED.value(), updatedInstitution);
	}

	@Override
	public CustomResponse<Institution> deleteInstitution(int instiId) {
		Institution institution = this.institutionRepository.findById(instiId).get();
		if (institution == null) {
			throw new NotFoundException("Institution with id " + instiId + " not found!");
		}
		Institution institutionToDelete = new Institution(institution);
		this.institutionRepository.deleteById(instiId);

		return new CustomResponse<>("Institute deleted successfully!", HttpStatus.ACCEPTED.value(),
				institutionToDelete);
	}

	@Override
	public CustomResponse<Institution> removeParty(int instiId, int partyId) {
		Institution institution = this.institutionRepository.findById(instiId).get();
		if (institution == null) {
			throw new NotFoundException("Institution with id " + instiId + " not found!");
		}
		List<Party> allParties = institution.getAllParties();
		for (int j = 0; j < allParties.size(); j++) {
			Party party = allParties.get(j);
			if (party.getPartyId() == partyId) {
				Party partyAtLastIndex = allParties.get(allParties.size() - 1);
				allParties.set(j, partyAtLastIndex);
				allParties.remove(allParties.size() - 1);
				institution = this.institutionRepository.save(institution);
				return new CustomResponse<>("Party deleted successfully!", HttpStatus.ACCEPTED.value(), institution);
			}
		}
		throw new NotFoundException("Party with id " + partyId + " not found!");
	}

}
