package com.osttra.capstone.tradeaggregation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osttra.capstone.tradeaggregation.entity.Party;

@Repository
public interface PartyRepository extends JpaRepository<Party, Integer> {

	Party findByPartyName(String name);

}
