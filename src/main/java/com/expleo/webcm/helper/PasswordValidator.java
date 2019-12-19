package com.expleo.webcm.helper;

import org.passay.*;

import java.util.Arrays;
import java.util.List;

/**
 * A helper class which is using Passay to validate a password.
 * */

public class PasswordValidator {

    public List<String> isValid(String password){

        org.passay.PasswordValidator validator = new org.passay.PasswordValidator(Arrays.asList(

                // at least 8 characters
                new LengthRule(8, 30),

                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),

                // no whitespace
                new WhitespaceRule()

//                // no common passwords
//                dictionaryRule
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return null;
        }

        return validator.getMessages(result);

    }
}
