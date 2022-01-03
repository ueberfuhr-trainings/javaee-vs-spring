package de.sample.javax.todos.domain;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DueDateValidator implements ConstraintValidator<DueDate, LocalDate> {

	private DueDate annotation;

	@Override
	public void initialize(DueDate constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		if(null != value) {
			if(value.isBefore(LocalDate.now())) {
				return false;
			}
			if(value.isAfter(LocalDate.now().plus(annotation.period(), annotation.unit()))) {
				return false;
			}
		}
		return true;
	}

}
