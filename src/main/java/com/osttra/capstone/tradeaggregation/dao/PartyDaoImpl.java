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

	@Override
	public Party save(Party party) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(party);
		return party;
	}

	@Override
	public Party getPartyByName(String name) {
		Session session = entityManager.unwrap(Session.class);
		Query theQuery = session.createQuery("from Party where partyName=:name");
		theQuery.setParameter("name", name);
		Party p = (Party) theQuery.getResultList().stream().findFirst().orElse(null);
		if (p == null)
			return null;
		if (p.getPartyName().equals(name)) {
			return p;
		}

		return null;
	}

	@Override
	public void deleteParty(int id) {
		Session session = entityManager.unwrap(Session.class);
		Query theQuery = session.createQuery("delete from Party where partyId=:id");
		theQuery.setParameter("id", id);
		theQuery.executeUpdate();
	}

}
