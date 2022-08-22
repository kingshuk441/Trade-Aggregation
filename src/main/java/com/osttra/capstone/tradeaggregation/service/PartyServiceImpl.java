package com.osttra.capstone.tradeaggregation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osttra.capstone.tradeaggregation.controller.FoundException;
import com.osttra.capstone.tradeaggregation.controller.NotFoundException;
import com.osttra.capstone.tradeaggregation.dao.InstitutionDao;
import com.osttra.capstone.tradeaggregation.dao.PartyDao;
import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Institution;
import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.entity.PartyBody;

@Service
public class PartyServiceImpl implements PartyService {
	@Autowired
	private PartyDao partyDao;
	@Autowired
	private InstitutionDao institutionDao;

	@Override
	@Transactional
	public CustomResponse<Party> getParties() {
		List<Party> list = this.partyDao.getParties();
		return new CustomResponse<>("all party fetched successfully!", HttpStatus.ACCEPTED.value(), list);
	}

	@Override
	@Transactional
	public CustomResponse<Party> getParty(int id) {
		Party p = this.partyDao.getParty(id);
		if (p == null) {
			throw new NotFoundException("party with id " + id + " not found!");
		}
		return new CustomResponse<>("party fetched successfully!", HttpStatus.ACCEPTED.value(), p);
	}

	@Override
	@Transactional
	public CustomResponse<Institution> getInstitution(int id) {
		Party p = this.partyDao.getParty(id);
		if (p == null) {
			throw new NotFoundException("party with id " + id + " not found!");
		}
		Institution i = p.getInstitution();
		return new CustomResponse<>("Institution fetched successfully!", HttpStatus.ACCEPTED.value(), i);
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
	@Transactional
	public CustomResponse<Party> addParty(PartyBody party) {
		String name = party.getPartyName();
		this.getPartyNameHelper(name);
		Party p = new Party();
		p.setPartyName(name);
		p.setPartyFullName(party.getPartyFullName());
		int institutionId = party.getInstitution();
		Institution i = this.institutionDao.getInstitution(institutionId);
		if (i == null) {
			throw new NotFoundException("Institution with name " + name + " not found!");
		}
		p.setInstitution(i);
		Party newParty = this.partyDao.save(p);
		return new CustomResponse<>("Party Added successfully!", HttpStatus.ACCEPTED.value(), newParty);
	}

	@Override
	@Transactional
	public CustomResponse<Party> getPartyByName(String name) {
		Party p = this.partyDao.getPartyByName(name);
		if (p == null) {
			throw new NotFoundException("Party with name " + name + " not found!");
		}

		return new CustomResponse<>("Party fetched successfully!", HttpStatus.ACCEPTED.value(), p);
	}

	@Override
	@Transactional
	public CustomResponse<Party> updateParty(int id, PartyBody party) {
		Party p = this.partyDao.getParty(id);
		if (p == null) {
			throw new NotFoundException("party with id " + id + " not found!");
		}
		String name = party.getPartyName();
		int institutionId = party.getInstitution();

		if (institutionId != 0) {
			Institution i = this.institutionDao.getInstitution(institutionId);
			if (i == null) {
				throw new NotFoundException("Institution with id " + id + " not found!");
			}
			p.setInstitution(i);
		}
		if (name != null) {
			p.setPartyName(name);
		}
		if (party.getPartyFullName() != null) {
			p.setPartyFullName(party.getPartyFullName());
		}
		p = this.partyDao.save(p);
		return new CustomResponse<>("Party Updated successfully!", HttpStatus.ACCEPTED.value(), p);
	}

	@Override
	@Transactional
	public CustomResponse<Party> deleteParty(int id) {
		Party p = this.partyDao.getParty(id);
		if (p == null) {
			throw new NotFoundException("party with id " + id + " not found!");
		}
		this.partyDao.deleteParty(id);
		return new CustomResponse<>("Party Deleted successfully!", HttpStatus.ACCEPTED.value(), p);
	}
}
