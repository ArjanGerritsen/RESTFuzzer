package nl.ou.se.rest.fuzzer.data.domain.rdm.factory;

import nl.ou.se.rest.fuzzer.data.domain.rdm.RdmAction;
import nl.ou.se.rest.fuzzer.data.domain.rdm.RdmResponseFactory;

public class RdmActionFactory {

	// variables
	private RdmAction rdmAction;
	private RdmParameterFactory rdmParameterFactory = new RdmParameterFactory();
	private RdmResponseFactory rdmResponseFactory = new RdmResponseFactory();

	// constructor
	public RdmActionFactory create(String url, String httpMethod) {
		rdmAction = new RdmAction(url, httpMethod);
		return this;
	}

	// methods
	public RdmActionFactory addParameter(String name, boolean required, String description, String context, String type,
			String pattern, String format) {
		int position = rdmAction.getParameters().size() + 1;
		rdmAction.getParameters().add(rdmParameterFactory
				.create(position, name, required, description, type, context, pattern, format).build());
		return this;
	}

	public RdmActionFactory addResponse(String statusCode, String description) {
		rdmAction.getResponses().add(rdmResponseFactory.create(Integer.valueOf(statusCode), description).build());
		return this;
	}

	public RdmAction build() {
		return rdmAction;
	}
}