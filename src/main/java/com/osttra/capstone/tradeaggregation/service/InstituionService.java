package com.osttra.capstone.tradeaggregation.service;

import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Institution;
import com.osttra.capstone.tradeaggregation.entity.Party;

public interface InstituionService {
	public CustomResponse<Institution> getInstitutions();

	public CustomResponse<Institution> getInstitution(int id);

	public CustomResponse<Party> getParties(int id);

	public CustomResponse<Institution> addParty(int id, int partyId);
}
