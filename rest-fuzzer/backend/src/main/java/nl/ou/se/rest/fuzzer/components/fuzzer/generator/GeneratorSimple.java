package nl.ou.se.rest.fuzzer.components.fuzzer.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.components.data.fuz.factory.FuzRequestFactory;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.ParameterContext;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdParameter;

@Service
public class GeneratorSimple {

	// variables
	private SortedSet<FuzRequest> requests = new TreeSet<>();

	private FuzRequestFactory requestFactory = new FuzRequestFactory();

	// methods
	public void start(FuzProject project) {
		project.getSut().getActions().forEach(a -> processAction(a));
	}

	private void processAction(RmdAction action) {
		requestFactory.create(action.getPath(), action.getHttpMethod());

		for (ParameterContext context : ParameterContext.values()) {
			requestFactory.addParameterMap(context, createParameterMap(action.getParametersByContext(context)));
		}

		this.requests.add(requestFactory.build());
	}

	private Map<String, Object> createParameterMap(List<RmdParameter> parameters) {
		Map<String, Object> parameterMap = new HashMap<>();

		parameters.forEach(parameter -> parameterMap.put(parameter.getName(), getValueForParameter(parameter)));

		return parameterMap;
	}

	private Object getValueForParameter(RmdParameter parameter) {
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

	// getters and setters
	public SortedSet<FuzRequest> getRequests() {
		return requests;
	}

	public void setRequests(SortedSet<FuzRequest> requests) {
		this.requests = requests;
	}
}
