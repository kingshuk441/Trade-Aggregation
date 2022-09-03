package com.osttra.capstone.tradeaggregation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osttra.capstone.tradeaggregation.entity.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {

	List<Trade> findByPartyName(String partyName);

	List<Trade> findByInstitutionId(int id);

}
