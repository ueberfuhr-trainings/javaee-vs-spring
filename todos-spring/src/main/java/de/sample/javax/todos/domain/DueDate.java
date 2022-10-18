package de.sample.javax.todos.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = DueDateValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DueDate {

	long period() default 6;
	
	ChronoUnit unit() default ChronoUnit.MONTHS; 

	String message() default "DueDate muss innerhalb {period} {unit} in der Zukunft liegen";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
