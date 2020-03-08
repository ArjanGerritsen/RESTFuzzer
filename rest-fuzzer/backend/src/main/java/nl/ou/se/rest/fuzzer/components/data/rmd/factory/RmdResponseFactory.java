package nl.ou.se.rest.fuzzer.components.data.rmd.factory;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdResponse;

public class RmdResponseFactory {

	// variables
	private RmdResponse response;

	// methods
	public RmdResponseFactory create(Integer statusCode, String description) {
		response = new RmdResponse(statusCode, description);
		return this;
	}

	public RmdResponse build() {
		return this.response;
	}
}