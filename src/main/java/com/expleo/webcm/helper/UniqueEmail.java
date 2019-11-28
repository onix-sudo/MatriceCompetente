package com.expleo.webcm.helper;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface UniqueEmail {

    String message() default "Adresa de email exista deja in baza de date.";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
