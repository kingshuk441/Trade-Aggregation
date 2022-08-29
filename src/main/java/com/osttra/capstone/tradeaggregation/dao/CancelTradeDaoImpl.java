package com.osttra.capstone.tradeaggregation.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.osttra.capstone.tradeaggregation.entity.CancelTrade;

@Repository
public class CancelTradeDaoImpl implements CancelTradeDao {
	@Autowired
	private EntityManager entityManager;

	@Override
	public CancelTrade addCancelTrade(CancelTrade cancel) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(cancel);
		return cancel;
	}

	@Override
	public List<CancelTrade> getCancelTrades() {
		Session session = entityManager.unwrap(Session.class);
		Query<CancelTrade> query = session.createQuery("from CancelTrade", CancelTrade.class);
		return query.getResultList();
	}

	@Override
	public CancelTrade getCancelTrade(int id) {
		Session session = entityManager.unwrap(Session.class);
		return session.get(CancelTrade.class, id);
	}

}
