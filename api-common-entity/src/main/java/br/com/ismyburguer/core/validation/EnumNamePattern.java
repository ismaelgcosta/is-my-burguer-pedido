package br.com.ismyburguer.core.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {
        EnumNamePatternValidator.class,
        EnumNamePatternStringValidator.class
})
public @interface EnumNamePattern {
    String regexp();
    String message() default "O campo informado não corresponde ao padrão \"{regexp}\"";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}