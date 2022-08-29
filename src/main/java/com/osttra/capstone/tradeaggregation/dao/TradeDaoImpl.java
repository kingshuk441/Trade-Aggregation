package com.osttra.capstone.tradeaggregation.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.osttra.capstone.tradeaggregation.entity.Trade;

@Repository
public class TradeDaoImpl implements TradeDao {
	@Autowired
	private EntityManager entityManager;

	@Override
	public Trade saveTrade(Trade body) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(body);
		return body;

	}

	@Override
	public List<Trade> getTrades() {
		Session session = entityManager.unwrap(Session.class);
		Query<Trade> query = session.createQuery("from Trade", Trade.class);
		List<Trade> allTrades = query.getResultList();
		return allTrades;

	}

	@Override
	public void deleteTrade(int id) {
		Session session = entityManager.unwrap(Session.class);
		Query theQuery = session.createQuery("delete from Trade where tradeId=:id");
		theQuery.setParameter("id", id);
		theQuery.executeUpdate();

	}

	@Override
	public Trade getTrade(int id) {
		Session session = entityManager.unwrap(Session.class);
		return session.get(Trade.class, id);
	}

}
