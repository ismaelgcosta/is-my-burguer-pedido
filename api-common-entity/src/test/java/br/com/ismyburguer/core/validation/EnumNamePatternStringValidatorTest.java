package br.com.ismyburguer.core.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnumNamePatternStringValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private EnumNamePatternStringValidator validator;

    private static final String VALID_PATTERN = "^[A-Z]+$";
    private static final String INVALID_PATTERN = "//\\[A-Z]+$92*";
    private static final String VALID_VALUE = "ENUM";
    private static final String INVALID_VALUE = "enum";

    @Test
    void isValid_ShouldReturnTrue_WhenValueIsNull() {
        // When
        boolean result = validator.isValid(null, context);

        // Then
        assertTrue(result);
    }

    @Test
    void isValid_ShouldReturnTrue_WhenValueMatchesPattern() {
        // When
        EnumNamePattern annotation = mock(EnumNamePattern.class);
        when(annotation.regexp()).thenReturn(VALID_PATTERN);
        validator.initialize(annotation);
        boolean result = validator.isValid(VALID_VALUE, context);

        // Then
        assertTrue(result);
    }

    @Test
    void isValid_ShouldReturnFalse_WhenValueDoesNotMatchPattern() {
        // When
        EnumNamePattern annotation = mock(EnumNamePattern.class);
        when(annotation.regexp()).thenReturn(VALID_PATTERN);
        validator.initialize(annotation);
        boolean result = validator.isValid(INVALID_VALUE, context);

        // Then
        assertFalse(result);
    }
}
