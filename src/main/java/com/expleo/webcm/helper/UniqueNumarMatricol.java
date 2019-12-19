package com.expleo.webcm.helper;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation to validate if a registration number is unique
 * */

@Constraint(validatedBy = UniqueNumarMatricolValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface UniqueNumarMatricol {

    public String message() default "Acest numar matricol exista deja in baza de date.";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
