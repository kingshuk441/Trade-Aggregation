package com.osttra.capstone.tradeaggregation.service;

import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Institution;
import com.osttra.capstone.tradeaggregation.entity.InstitutionBody;
import com.osttra.capstone.tradeaggregation.entity.Party;

public interface InstitutionService {
	public CustomResponse<Institution> getInstitutions();

	public CustomResponse<Institution> getInstitution(int id);

	public CustomResponse<Party> getParties(int id);

	public CustomResponse<Institution> addParty(int id, int partyId);

	public CustomResponse<Institution> addInstitution(InstitutionBody body);

	public CustomResponse<Institution> getInstitutionByName(String name);

	public CustomResponse<Institution> updateInstitution(int id, InstitutionBody body);

	public CustomResponse<Institution> deleteInstitution(int id);

	public CustomResponse<Institution> removeParty(int id, int partyId);
}
