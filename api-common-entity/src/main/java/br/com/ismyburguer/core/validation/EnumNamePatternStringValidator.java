package br.com.ismyburguer.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnumNamePatternStringValidator implements ConstraintValidator<EnumNamePattern, String> {
    private Pattern pattern;

    @Override
    public void initialize(EnumNamePattern annotation) {
        pattern = Pattern.compile(annotation.regexp());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Matcher m = pattern.matcher(value);
        return m.matches();
    }
}