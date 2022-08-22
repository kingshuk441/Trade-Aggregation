package com.osttra.capstone.tradeaggregation.service;

import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Institution;
import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.entity.PartyBody;

public interface PartyService {
	public CustomResponse<Party> getParties();

	public CustomResponse<Party> getParty(int id);

	public CustomResponse<Institution> getInstitution(int id);

	public CustomResponse<Party> addParty(PartyBody p);

	public CustomResponse<Party> getPartyByName(String name);

	public CustomResponse<Party> updateParty(int id, PartyBody party);

	public CustomResponse<Party> deleteParty(int id);
}
