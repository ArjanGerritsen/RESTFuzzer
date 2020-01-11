package nl.ou.se.fuzz.rest.service.shared;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class JpaUtil {

	public static List<String> getViolations(Object o) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator.validate(o).stream().map(v -> String.format("%s: %s", v.getPropertyPath(), v.getMessage())).collect(Collectors.toList());
	}
}
