package com.osttra.capstone.tradeaggregation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.osttra.capstone.tradeaggregation.customexception.NotFoundException;
import com.osttra.capstone.tradeaggregation.service.TradeService;

public class UniqueTrnValidator implements ConstraintValidator<UniqueTrn, String> {
	@Autowired
	private TradeService tradeService;

	static private String prevTrn;

	@Override
	public void initialize(UniqueTrn constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.length() == 0)
			return true;
		if (prevTrn == null) {
			prevTrn = value;
			return true;
		} else {
			try {
				this.tradeService.findByTrnParty(prevTrn, value);
			} catch (NotFoundException e) {

				prevTrn = null;
				return true;
			}
		}
		prevTrn = null;
		return false;
	}
}
