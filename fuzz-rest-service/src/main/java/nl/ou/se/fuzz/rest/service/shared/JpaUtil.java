package nl.ou.se.fuzz.rest.service.shared;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class JpaUtil {

	public static String getViolations(Object o) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> violations = validator.validate(o);
		StringBuilder violationString = new StringBuilder();
		violations.forEach(v -> violationString.append(v.getMessage() + "\n"));
		return violationString.toString();
	}
}