package ch.haeuslers.bookr.entity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BookingValidator implements ConstraintValidator<BookingCheck, Booking> {

    @Override
    public void initialize(BookingCheck constraintAnnotation) {
        // nothing to do
    }

    @Override
    public boolean isValid(Booking value, ConstraintValidatorContext context) {
        return value.getStart().before(value.getEnd());
    }
}
