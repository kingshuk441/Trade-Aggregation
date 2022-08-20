package com.osttra.capstone.tradeaggregation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osttra.capstone.tradeaggregation.dao.PartyDao;
import com.osttra.capstone.tradeaggregation.entity.Party;

@Service
public class PartyServiceImpl implements PartyService {
	@Autowired
	private PartyDao partyDao;

	@Override
	@Transactional
	public List<Party> getParties() {
		return this.partyDao.getParties();
	}

	@Override
	@Transactional
	public Party getParty(int id) {
		return this.partyDao.getParty(id);
	}

}
