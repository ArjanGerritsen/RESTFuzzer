package nl.ou.se.rest.fuzzer.components.data.fuz.factory;

import java.time.LocalDateTime;
import java.util.Map;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.HttpMethod;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.ParameterContext;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;

public class FuzRequestFactory {

	private FuzRequest request;

	public FuzRequestFactory create(FuzProject project, RmdAction action, String path, HttpMethod httpMethod) {
		request = new FuzRequest();
		request.setProject(project);
		request.setAction(action);
		request.setPath(path);
		request.setHttpMethod(httpMethod);
		request.setCreatedAt(LocalDateTime.now());
		return this;
	}

	public FuzRequestFactory addParameterMap(ParameterContext context, Map<String, Object> parameterMap) {
		request.setParameterMap(context, parameterMap);
		return this;
	}

	public FuzRequest build() {
		return this.request;
	}
}
