package com.osttra.capstone.tradeaggregation.dao;

import java.util.List;

import com.osttra.capstone.tradeaggregation.entity.Institution;
import com.osttra.capstone.tradeaggregation.entity.Party;

public interface InstitutionDao {
	public List<Institution> getInstitutions();

	public Institution getInstitution(int id);

	public List<Party> getParties(Institution i, int id);

	public Institution addParty(int id, Party p);

	public Institution saveInstitution(Institution body);

	public Institution getInstitutionByName(String name);

	public void deleteInstitution(int id);

}
