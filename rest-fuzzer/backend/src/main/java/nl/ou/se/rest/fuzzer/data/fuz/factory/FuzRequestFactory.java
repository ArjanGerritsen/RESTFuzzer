package nl.ou.se.rest.fuzzer.data.fuz.factory;

import java.time.LocalDateTime;
import java.util.Map;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.data.rmd.domain.HttpMethod;
import nl.ou.se.rest.fuzzer.data.rmd.domain.ParameterContext;

public class FuzRequestFactory {

	private FuzRequest request;

	public FuzRequestFactory create(String path, HttpMethod httpMethod) {
		request = new FuzRequest();
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
