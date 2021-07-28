package com.person.apirestperson.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ FIELD, METHOD })
@Retention(RUNTIME)
@Constraint(validatedBy = CepValidator.class)
public @interface Cep {

	Class<?>[] groups() default {};
	
	//String message() default "{br.gov.frameworkdemoiselle.cep}";
	String message() default "Cep invalid: the correct format is XXXXXXXX";

	Class<? extends Payload>[] payload() default {};

}
