package com.osttra.capstone.tradeaggregation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.repository.PartyRepository;

public class PartyNameValidator implements ConstraintValidator<PartyName, String> {

	@Autowired
	private PartyRepository partyRepository;

	@Override
	public void initialize(PartyName partyName) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null)
			return true;
		Party p = this.partyRepository.findByPartyName(value);
		if (p != null && p.getInstitution() != null)
			return true;
		return false;

	}

}
