package com.osttra.capstone.tradeaggregation.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.osttra.capstone.tradeaggregation.entity.Party;

@Repository
public class PartyDaoImpl implements PartyDao {
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Party> getParties() {
		Session session = entityManager.unwrap(Session.class);
		Query<Party> query = session.createQuery("from Party", Party.class);
		List<Party> parties = query.getResultList();
		return parties;
	}

	@Override
	public Party getParty(int id) {
		Session session = entityManager.unwrap(Session.class);
		return session.get(Party.class, id);

	}

}
