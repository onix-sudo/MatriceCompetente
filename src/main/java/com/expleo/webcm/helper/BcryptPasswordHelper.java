package com.expleo.webcm.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordHelper {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String generateFirstPassword(String nume, int numarMatricol){
        String input = nume+numarMatricol;
        String password = "{bcrypt}"+passwordEncoder.encode(input);

        return password;
    }

    public String makePassword(String nume, int numarMatricol){
        String passwordGenerated = generateFirstPassword(nume, numarMatricol);
        return passwordGenerated;
    }

    public String generatePassword(String input){
       String password = "{bcrypt}"+passwordEncoder.encode(input);
       return password;
    }

    public boolean checkIfValidOldPassowrd(String oldPassword, String userPassword){
        return passwordEncoder.matches(oldPassword, userPassword.substring(8));
    }
}
