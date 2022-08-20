package com.osttra.capstone.tradeaggregation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osttra.capstone.tradeaggregation.controller.NotFoundException;
import com.osttra.capstone.tradeaggregation.dao.InstitutionDao;
import com.osttra.capstone.tradeaggregation.dao.PartyDao;
import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Institution;
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
		return new CustomResponse<>("added successfully!", HttpStatus.ACCEPTED.value(), i);

	}
}
