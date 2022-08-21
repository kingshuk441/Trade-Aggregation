package com.osttra.capstone.tradeaggregation.service;

import java.util.ArrayList;
import java.util.HashSet;
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
import com.osttra.capstone.tradeaggregation.entity.InstitutionBody;
import com.osttra.capstone.tradeaggregation.entity.Party;

@Service
public class InstitutionServiceImpl implements InstituionService {
	@Autowired
	private InstitutionDao institutionDao;
	@Autowired
	private PartyDao partyDao;

	@Override
	@Transactional
	public CustomResponse<Institution> getInstitutions() {
		List<Institution> list = this.institutionDao.getInstitutions();
		return new CustomResponse<>("all institution fetched successfully!", HttpStatus.ACCEPTED.value(), list);
	}

	@Override
	@Transactional
	public CustomResponse<Institution> getInstitution(int id) {
		Institution i = this.institutionDao.getInstitution(id);
		if (i == null) {
			throw new NotFoundException("Institution with id " + id + " not found!");
		}
		return new CustomResponse<>("institution of id " + id + " fetched successfully!", HttpStatus.ACCEPTED.value(),
				i);
	}

	@Override
	@Transactional
	public CustomResponse<Party> getParties(int id) {
		Institution i = this.institutionDao.getInstitution(id);
		if (i == null) {
			throw new NotFoundException("Institution with id " + id + " not found!");
		}
		List<Party> p = this.institutionDao.getParties(i, id);
		return new CustomResponse<>("parties fetched successfully!", HttpStatus.ACCEPTED.value(), p);
	}

	@Override
	@Transactional
	public CustomResponse<Institution> addParty(int id, int partyId) {
		Party p = this.partyDao.getParty(partyId);
		if (p == null) {
			throw new NotFoundException("party with id " + id + " not found!");
		}
		Institution i = this.institutionDao.addParty(id, p);
		if (i == null) {
			throw new RuntimeException("already added!");
		}
		return new CustomResponse<>("party added successfully!", HttpStatus.ACCEPTED.value(), i);

	}

	@Override
	@Transactional
	public CustomResponse<Institution> addInstituion(Institution body) {
		String name = body.getInstitutionName();
		this.getInstituteNameHelper(name);
		body.setInstitutionId(0);
		Institution i = this.institutionDao.saveInstituion(body);
		return new CustomResponse<>("institution added successfully!", HttpStatus.ACCEPTED.value(), i);
	}

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
	@Transactional
	public CustomResponse<Institution> getInstitutionByName(String name) {
		Institution i = this.institutionDao.getInstitutionByName(name);
		if (i == null) {
			throw new NotFoundException("Institution with name " + name + " not found!");
		}

		return new CustomResponse<>("Institute fetched successfully!", HttpStatus.ACCEPTED.value(), i);
	}

	@Override
	@Transactional
	public CustomResponse<Institution> updateInstitution(int id, InstitutionBody body) {
		Institution i = this.institutionDao.getInstitution(id);
		if (i == null) {
			throw new NotFoundException("Institution with id " + id + " not found!");
		}
		if (body.getInstitutionName() != null) {
			i.setInstitutionName(body.getInstitutionName());
		}
		if (body.getParties() != null) {
			HashSet<Integer> vis = new HashSet<>();
			List<Party> addParties = new ArrayList<>();
			for (int idx : body.getParties()) {
				Party p = this.partyDao.getParty(idx);
				addParties.add(p);
				vis.add(idx);
			}
			for (Party p : i.getAllParties()) {
				if (vis.contains(p.getPartyId()) == false) {
					addParties.add(p);
					vis.add(p.getPartyId());
				}
			}
			i.setAllParties(addParties);
		}
		Institution updatedInstitution = this.institutionDao.saveInstituion(i);
		return new CustomResponse<>("Institute updated successfully!", HttpStatus.ACCEPTED.value(), updatedInstitution);
	}

	@Override
	@Transactional
	public CustomResponse<Institution> deleteInstitution(int id) {
		Institution i = this.institutionDao.getInstitution(id);
		if (i == null) {
			throw new NotFoundException("Institution with id " + id + " not found!");
		}
		Institution d = new Institution(i);
		this.institutionDao.deleteInstitution(id);

		return new CustomResponse<>("Institute deleted successfully!", HttpStatus.ACCEPTED.value(), d);
	}

}
