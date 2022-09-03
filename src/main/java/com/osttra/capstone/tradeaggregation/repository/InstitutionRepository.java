package com.osttra.capstone.tradeaggregation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osttra.capstone.tradeaggregation.entity.Institution;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Integer> {

	Institution findByInstitutionName(String name);

}
