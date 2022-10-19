package de.deutscherv.gb0500.schulung.spring.boundary;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import de.deutscherv.gb0500.schulung.common.domain.Todo;

@Validated
@Service
public class TodoValidator {
	
	void validate(@Valid Todo todo) {
		// nothing to impl
	}

}
