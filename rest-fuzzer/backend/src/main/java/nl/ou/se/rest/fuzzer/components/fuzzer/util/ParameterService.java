package nl.ou.se.rest.fuzzer.components.fuzzer.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdParameter;

@Service
public class ParameterService {

	public Map<String, Object> createParameterMap(List<RmdParameter> parameters) {
		Map<String, Object> parameterMap = new HashMap<>();

		parameters.forEach(parameter -> parameterMap.put(parameter.getName(), getValueForParameter(parameter)));

		return parameterMap;
	}

	private static Object getValueForParameter(RmdParameter parameter) {
		switch (parameter.getType()) {
		case BOOLEAN:
			return true;
		case STRING:
			return "abc";
		case INTEGER:
			return 1;
		case ARRAY:
			return null;
		default:
			return "?"; // TODO dit zou niet voor mogen komen + zinvolle waarden fuzzen ...
		}
	}
}