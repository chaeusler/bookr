package ch.haeuslers.bookr.entity;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {BookingValidator.class})
@Documented
public @interface BookingCheck  {
}
