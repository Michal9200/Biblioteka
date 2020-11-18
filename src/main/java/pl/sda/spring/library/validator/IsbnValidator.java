package pl.sda.spring.library.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {

    @Override
    public void initialize(Isbn constraintAnnotation) {

    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        return isbn != null && (isbn.length() == 10 || isbn.length() == 13);
    }
}
