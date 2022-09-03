package com.osttra.capstone.tradeaggregation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.repository.PartyRepository;

public class PartySameValidator implements ConstraintValidator<PartySame, String> {

	static String def;

	@Autowired
	private PartyRepository partyRepository;

	@Override
	public void initialize(PartySame partyName) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.length() == 0) {
			def = null;
			return true;
		}

		Party p = this.partyRepository.findByPartyName(value);
		if (p == null) {
			def = null;
			return true;
		}

		String institution = p.getInstitution().getInstitutionName();

		if (def == null) {
			def = institution;
			return true;
		} else if (def.equals(institution)) {
			def = null;
			return false;
		}
		def = null;
		return true;

	}
}
