package com.osttra.capstone.tradeaggregation.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.osttra.capstone.tradeaggregation.entity.Institution;
import com.osttra.capstone.tradeaggregation.entity.Party;

@Repository
public class InstitutionDaoImpl implements InstitutionDao {
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Institution> getInstitutions() {
		Session session = entityManager.unwrap(Session.class);
		Query<Institution> query = session.createQuery("from Institution", Institution.class);
		return query.getResultList();
	}

	@Override
	public Institution getInstitution(int id) {
		Session session = entityManager.unwrap(Session.class);
		return session.get(Institution.class, id);

	}

	@Override
	public List<Party> getParties(Institution i, int id) {
		return i.getAllParties();
	}

	@Override
	public Institution addParty(int id, Party party) {
		Session session = entityManager.unwrap(Session.class);
		Institution in = session.get(Institution.class, id);
		for (Party p : in.getAllParties()) {
			if (p.getPartyId() == party.getPartyId()) {
				return null;
			}
		}
		in.addParty(party);
		return in;

	}
}
