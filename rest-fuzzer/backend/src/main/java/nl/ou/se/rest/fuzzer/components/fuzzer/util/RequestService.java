package nl.ou.se.rest.fuzzer.components.fuzzer.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.components.data.fuz.factory.FuzRequestFactory;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.ParameterContext;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;

@Service
public class RequestService {

	// variables
	private static FuzRequestFactory requestFactory = new FuzRequestFactory();

	@Autowired
	private ParameterService parameterService;

	public FuzRequest getRequestFromAction(FuzProject project, RmdAction action) {
		requestFactory.create(project, action.getPath(), action.getHttpMethod());

		for (ParameterContext context : ParameterContext.values()) {
			requestFactory.addParameterMap(context,
					parameterService.createParameterMap(action.getParametersByContext(context)));
		}

		return requestFactory.build();
	}
}