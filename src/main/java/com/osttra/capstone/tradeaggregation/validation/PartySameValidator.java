package com.osttra.capstone.tradeaggregation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PartySameValidator implements ConstraintValidator<PartySame, String> {

	static String def;

	@Override
	public void initialize(PartySame partyName) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
//		System.out.println("-------------------------");
//		System.out.println("VAL:" + value);
//		System.out.println("DEF:" + def);
		if (value == null)
			return true;
//		if (def != null)
//			System.out.println("COM:" + def.equals(value));

		if (def == null) {
			def = value;
//			System.out.println("FIRST:" + value);
			return true;
		} else if (def.equals(value)) {
			def = null;
//			System.out.println("SEC:" + value);
			return false;
		}
		def = null;
		return true;

	}
}
