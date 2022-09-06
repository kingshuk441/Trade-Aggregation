package com.osttra.capstone.tradeaggregation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueTrnValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTrn {
	public String value() default "def";

	public String message() default "TRN must be unique";

	public Class<?>[] groups() default {};

	public Class<? extends Payload>[] payload() default {};
}
