package com.expleo.webcm.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A helper class which generate encrypted password
 * */
@Component
public class BcryptPasswordHelper {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //Generate a new password by nume and numarMatricol
    private String generateFirstPassword(String nume, int numarMatricol){
        String input = nume+numarMatricol;
        return "{bcrypt}"+passwordEncoder.encode(input);
    }

    //Returns an encrypted password
    public String makePassword(String nume, int numarMatricol){
        return generateFirstPassword(nume, numarMatricol);
    }

    //Encrypts a wanted password
    public String generatePassword(String input){
       return "{bcrypt}"+passwordEncoder.encode(input);
    }

    //Checks if the old password is the right one
    public boolean checkIfValidOldPassowrd(String oldPassword, String userPassword){
        return passwordEncoder.matches(oldPassword, userPassword.substring(8));
    }
}
