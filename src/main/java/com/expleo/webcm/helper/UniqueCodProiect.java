package com.expleo.webcm.helper;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = UniqueCodProiectValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface UniqueCodProiect {

    String message() default "Acest cod de proiect exista deja in baza de date.";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
