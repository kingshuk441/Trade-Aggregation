package com.osttra.capstone.tradeaggregation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Trade;
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
			CustomResponse<Trade> c = this.tradeService.findByTrnParty(prevTrn, value);
			if (c.getData() == null) {
				prevTrn = null;
				return true;
			}
		}
		prevTrn = null;
		return false;
	}
}
