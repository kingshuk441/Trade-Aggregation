package com.osttra.capstone.tradeaggregation.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
		Optional<Institution> institution = this.institutionRepository.findById(instiId);
		if (institution.isEmpty()) {
			throw new NotFoundException("Institution with id " + instiId + " not found!");
		}
		return new CustomResponse<>("institution of id " + instiId + " fetched successfully!",
				HttpStatus.ACCEPTED.value(), institution.get());
	}

	@Override
	public CustomResponse<Party> getParties(int instiId) {
		Optional<Institution> institution = this.institutionRepository.findById(instiId);
		if (institution.isEmpty()) {
			throw new NotFoundException("Institution with id " + instiId + " not found!");
		}
		List<Party> allParties = institution.get().getAllParties();
		return new CustomResponse<>("parties fetched successfully!", HttpStatus.ACCEPTED.value(), allParties);
	}

	private Institution addParty(int instiId, Party partyToAdd) {

		Optional<Institution> institution = this.institutionRepository.findById(instiId);
		for (Party party : institution.get().getAllParties()) {
			if (party.getPartyId() == partyToAdd.getPartyId()) {
				return null;
			}
		}

		institution.get().addParty(partyToAdd);
		return institution.get();

	}

	@Override
	public CustomResponse<Institution> addParty(int instiId, int partyId) {
		Optional<Party> party = this.partyRepository.findById(partyId);
		if (party.isEmpty()) {
			throw new NotFoundException("party with id " + partyId + " not found!");
		}
		Optional<Institution> inst = this.institutionRepository.findById(instiId);
		if (inst.isEmpty()) {
			throw new NotFoundException("institution with id " + instiId + " not found!");
		}
		Institution institution = this.addParty(instiId, party.get());
		if (institution == null) {
			throw new FoundException("party was already added!");
		}
		institution = this.institutionRepository.save(institution);
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
					Optional<Party> party = this.partyRepository.findById(partyId);
					if (party.isEmpty() == false)
						institution.addParty(party.get());
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
		Optional<Institution> institution = this.institutionRepository.findById(instiId);
		if (institution.isEmpty()) {
			throw new NotFoundException("Institution with id " + instiId + " not found!");
		}
		if (body.getInstitutionName() != null) {
			institution.get().setInstitutionName(body.getInstitutionName());
		}
		if (body.getParties() != null) {
			HashSet<Integer> alreadyPresent = new HashSet<>();
			List<Party> addParties = new ArrayList<>();
			for (int partyId : body.getParties()) {
				Optional<Party> party = this.partyRepository.findById(partyId);
				if (party.isEmpty() == false) {
					addParties.add(party.get());
					alreadyPresent.add(partyId);
				}
			}
			for (Party party : institution.get().getAllParties()) {
				if (alreadyPresent.contains(party.getPartyId()) == false) {
					addParties.add(party);
					alreadyPresent.add(party.getPartyId());
				}
			}
			institution.get().setAllParties(addParties);
		}
		Institution updatedInstitution = this.institutionRepository.save(institution.get());
		return new CustomResponse<>("Institute updated successfully!", HttpStatus.ACCEPTED.value(), updatedInstitution);
	}

	@Override
	public CustomResponse<Institution> deleteInstitution(int instiId) {
		Optional<Institution> institution = this.institutionRepository.findById(instiId);
		if (institution.isEmpty()) {
			throw new NotFoundException("Institution with id " + instiId + " not found!");
		}
		Institution institutionToDelete = new Institution(institution.get());
		this.institutionRepository.deleteById(instiId);

		return new CustomResponse<>("Institute deleted successfully!", HttpStatus.ACCEPTED.value(),
				institutionToDelete);
	}

	@Override
	public CustomResponse<Institution> removeParty(int instiId, int partyId) {
		Optional<Institution> institution = this.institutionRepository.findById(instiId);
		if (institution.isEmpty()) {
			throw new NotFoundException("Institution with id " + instiId + " not found!");
		}
		List<Party> allParties = institution.get().getAllParties();
		for (int j = 0; j < allParties.size(); j++) {
			Party party = allParties.get(j);
			if (party.getPartyId() == partyId) {
				Party partyAtLastIndex = allParties.get(allParties.size() - 1);
				allParties.set(j, partyAtLastIndex);
				allParties.remove(allParties.size() - 1);
				Institution updatedInstitution = this.institutionRepository.save(institution.get());
				return new CustomResponse<>("Party deleted successfully!", HttpStatus.ACCEPTED.value(),
						updatedInstitution);
			}
		}
		throw new NotFoundException("Party with id " + partyId + " not found!");
	}

}
