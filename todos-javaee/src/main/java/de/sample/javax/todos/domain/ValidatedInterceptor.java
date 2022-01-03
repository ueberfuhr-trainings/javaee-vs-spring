package de.sample.javax.todos.domain;

import java.util.Set;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

@Interceptor
@Validated
public class ValidatedInterceptor {
	
	@Inject
	Validator validator;
	
	@AroundInvoke
	public Object executeValidation(InvocationContext ic) throws Exception {
		// optionale Logik VOR Methodenausführung
		for(Object param : ic.getParameters()) {
			Set<ConstraintViolation<Object>> violations = validator.validate(param);
			if(!violations.isEmpty()) {
				StringBuilder message = new StringBuilder();
				for (ConstraintViolation<Object> v : violations) {
					message
						.append(v.getPropertyPath())
						.append(" ")
						.append(v.getMessage())
						.append("\n");
				}
				throw new ValidationException(message.toString());
			}
		}
		return ic.proceed(); // Aufrufen der Original-Methode
		// optionale Logik NACH Methodenausführung
	}

}
