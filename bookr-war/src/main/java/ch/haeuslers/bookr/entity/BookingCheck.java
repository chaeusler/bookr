package ch.haeuslers.bookr.entity;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {BookingValidator.class})
@Documented
public @interface BookingCheck  {
    String message() default "start date before end date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
