package com.osttra.capstone.tradeaggregation.dao;

import java.util.List;

import com.osttra.capstone.tradeaggregation.entity.Party;

public interface PartyDao {
	public List<Party> getParties();

	public Party getParty(int id);

}
