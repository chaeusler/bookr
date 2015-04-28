package ch.haeuslers.bookr.entity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UUIDValidator implements ConstraintValidator<UUID, String> {
    @Override
    public void initialize(UUID constraintAnnotation) {
        // nothing to do
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            return java.util.UUID.fromString(value).toString().equals(value);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
