package com.expleo.webcm.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BcryptPasswordGenerator {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String generatePassword(String nume, int numarMatricol){
        String input = nume+numarMatricol;
        String password = "{bcrypt}"+passwordEncoder.encode(input);

        return password;
    }

    public String makePassword(String nume, int numarMatricol){
        String passwordGenerated = generatePassword(nume, numarMatricol);
        return passwordGenerated;
    }
}
