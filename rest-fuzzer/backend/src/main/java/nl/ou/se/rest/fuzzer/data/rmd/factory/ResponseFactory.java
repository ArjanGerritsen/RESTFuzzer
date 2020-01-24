package nl.ou.se.rest.fuzzer.data.rmd.factory;

import nl.ou.se.rest.fuzzer.data.rmd.domain.Response;

public class ResponseFactory {

	// variables
	private Response response;

	// methods
	public ResponseFactory create(Integer statusCode, String description) {
		response = new Response(statusCode, description);
		return this;
	}

	public Response build() {
		return this.response;
	}
}