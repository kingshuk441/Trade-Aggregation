package com.osttra.capstone.tradeaggregation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.osttra.capstone.tradeaggregation.dao.PartyDaoImpl;
import com.osttra.capstone.tradeaggregation.entity.Party;

public class PartyNameValidator implements ConstraintValidator<PartyName, String> {

	@Autowired
	private PartyDaoImpl partyDao;
	String x;

	@Override
	public void initialize(PartyName partyName) {
		x = partyName.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null)
			return true;
		Party p = this.partyDao.getPartyByName(value);
		if (p != null && p.getInstitution() != null)
			return true;
		return false;

	}

}
