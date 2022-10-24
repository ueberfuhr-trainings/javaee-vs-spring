package de.sample.javax.spring.boundary;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import de.sample.javax.common.domain.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler 
		extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	void handleNotFoundException() {}
	
	@ExceptionHandler({
		ValidationException.class,
		ConstraintViolationException.class
	})
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	void handleValidationException() {}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super
				.handleMethodArgumentNotValid(ex, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}

}
