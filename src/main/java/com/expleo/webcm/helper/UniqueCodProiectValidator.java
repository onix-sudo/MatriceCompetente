package com.expleo.webcm.helper;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.service.ProiectService;
import com.expleo.webcm.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


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
