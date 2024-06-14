package org.oauth2.shopapp.validation;

import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DobValidation.class})
public @interface DobConstraint {
    String message() default "Dob invalid";
    int min();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
