package nl.ou.se.rest.fuzzer.components.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidatorUtil {

	// methods
	public static List<String> getViolations(Object o) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator.validate(o).stream().map(v -> String.format("%s: %s", v.getPropertyPath(), v.getMessage()))
				.collect(Collectors.toList());
	}
}
