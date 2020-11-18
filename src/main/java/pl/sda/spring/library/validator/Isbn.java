package pl.sda.spring.library.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsbnValidator.class)
public @interface Isbn {

    String message() default "ISBN should not be empty and should have 10 or 13 signs";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
