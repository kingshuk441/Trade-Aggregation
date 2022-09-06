package com.osttra.capstone.tradeaggregation.validation;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDateValidator implements ConstraintValidator<ValidDate, LocalDate> {

	@Override
	public void initialize(ValidDate constraintAnnotation) {

	}

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		if (value == null)
			return true;
		if (value.compareTo(LocalDate.now()) < 0)
			return false;

		return true;
	}

}
