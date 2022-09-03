package com.osttra.capstone.tradeaggregation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.osttra.capstone.tradeaggregation.entity.CancelTrade;

public interface CancelRepository extends JpaRepository<CancelTrade, Integer> {

}
