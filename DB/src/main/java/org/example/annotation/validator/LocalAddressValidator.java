package org.example.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.annotation.LocalAddress;

import java.util.regex.Pattern;

public class LocalAddressValidator implements ConstraintValidator<LocalAddress, String> {

    private String regexp;

    @Override
    public void initialize(LocalAddress constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.matches(regexp,s);
    }
}
