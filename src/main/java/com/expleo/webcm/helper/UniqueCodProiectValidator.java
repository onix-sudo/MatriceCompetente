package com.expleo.webcm.helper;

import com.expleo.webcm.service.ProiectService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * This class is returning if the code project checked by annotation UniqueCodProiect is unique;
 * */

public class UniqueCodProiectValidator implements ConstraintValidator<UniqueCodProiect, String> {

    @Autowired
    private ProiectService proiectService;


    @Override
    public void initialize(UniqueCodProiect constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !proiectService.foundCodProiectExpleo(value);
    }
}
