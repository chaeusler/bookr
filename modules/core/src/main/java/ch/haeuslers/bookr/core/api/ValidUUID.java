package ch.haeuslers.bookr.core.api;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UUIDValidator.class})
@Documented
public @interface ValidUUID {
    String message() default "invalid UUID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
