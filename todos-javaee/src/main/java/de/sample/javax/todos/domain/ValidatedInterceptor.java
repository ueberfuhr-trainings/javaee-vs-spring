package de.sample.javax.todos.domain;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Stream;

@Interceptor
@Validated
public class ValidatedInterceptor {

    @Inject
    private Validator validator;

    @AroundInvoke
    public Object validateMethodParameters(InvocationContext ic) throws Exception {
        final var paramAnnotations = ic.getMethod().getParameterAnnotations();
        final var paramValues = ic.getParameters();
        for (int i = 0; i < paramAnnotations.length; i++) {
            boolean shouldValidate = Stream.of(paramAnnotations[i])
              .anyMatch(Valid.class::isInstance);
            if (shouldValidate) {
                Set<ConstraintViolation<Object>> violations = validator.validate(paramValues[i]);
                if (!violations.isEmpty()) {
                    throw new ConstraintViolationException(violations);
                }

            }
        }
        return ic.proceed();
    }

}
