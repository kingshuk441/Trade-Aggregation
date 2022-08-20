package com.osttra.capstone.tradeaggregation.service;

import java.util.List;

import com.osttra.capstone.tradeaggregation.entity.Party;

public interface PartyService {
	public List<Party> getParties();

	public Party getParty(int id);
}
