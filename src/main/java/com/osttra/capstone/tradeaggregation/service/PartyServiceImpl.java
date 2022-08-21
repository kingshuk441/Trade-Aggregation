package com.osttra.capstone.tradeaggregation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osttra.capstone.tradeaggregation.controller.NotFoundException;
import com.osttra.capstone.tradeaggregation.dao.PartyDao;
import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Party;

@Service
public class PartyServiceImpl implements PartyService {
	@Autowired
	private PartyDao partyDao;

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

}
