package br.com.ismyburguer.core.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.util.TestPropertyValues;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnumNamePatternValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private EnumNamePatternValidator validator;

    private static final String VALID_PATTERN = "SYSTEM_ENVIRONMENT";

    @Test
    void isValid_ShouldReturnTrue_WhenValueIsNull() {
        EnumNamePattern annotation = mock(EnumNamePattern.class);
        when(annotation.regexp()).thenReturn(VALID_PATTERN);
        validator.initialize(annotation);

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
        boolean result = validator.isValid(TestPropertyValues.Type.SYSTEM_ENVIRONMENT, context);

        // Then
        assertTrue(result);
    }

    @Test
    void isValid_ShouldReturnFalse_WhenValueDoesNotMatchPattern() {
        // When
        EnumNamePattern annotation = mock(EnumNamePattern.class);
        when(annotation.regexp()).thenReturn(VALID_PATTERN);
        validator.initialize(annotation);
        boolean result = validator.isValid(TestPropertyValues.Type.MAP, context);

        // Then
        assertFalse(result);
    }
}