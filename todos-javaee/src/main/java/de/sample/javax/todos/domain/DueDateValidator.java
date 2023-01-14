package de.sample.javax.todos.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DueDateValidator implements ConstraintValidator<DueDate, LocalDate> {

	private DueDate annotation;

	@Override
	public void initialize(DueDate constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		if (null != value) {
			var now = LocalDate.now();
			var latest = now.plus(annotation.period(), annotation.unit());
			return !value.isBefore(now) && !value.isAfter(latest);
		} else {
			return true;
		}
	}

}
