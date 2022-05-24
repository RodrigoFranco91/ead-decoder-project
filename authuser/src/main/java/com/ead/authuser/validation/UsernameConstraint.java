package com.ead.authuser.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = UsernameConstraintImpl.class)
@Target({ METHOD, FIELD})
@Retention(RUNTIME)
public @interface UsernameConstraint {

    String message() default "Invalid username";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
