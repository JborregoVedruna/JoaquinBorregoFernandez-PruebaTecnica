package com.caixabank.loansmanager.domain.validations.validators;

import com.caixabank.loansmanager.domain.validations.DNI;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DNIValidator implements ConstraintValidator<DNI, String> {
    private static final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";

    @Override
    public void initialize(DNI constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.info("Validating DNI: {}", value);
        try {
            int number = Integer.parseInt(value.substring(0, 8));
            char character = value.charAt(8);

            int rm = number % 23;
            char dniChar = LETRAS.charAt(rm);
            log.info("Number: {}", number, "must have letter: {}", dniChar);
            return character == dniChar;
        } catch (NumberFormatException e) {
            log.warn("Invalid DNI format: {}", value);
            return false;
        }
    }
}
