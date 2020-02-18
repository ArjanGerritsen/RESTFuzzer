package nl.ou.se.rest.fuzzer.data.fuz.factory;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzResponse;

public class FuzResponseFactory {

	private FuzResponse response;

	public FuzResponseFactory create(FuzProject project, FuzRequest request) {
		response = new FuzResponse();
		response.setProject(project);
		response.setRequest(request);

		return this;
	}
	
	public FuzResponseFactory setCode(int statusCode) {
		response.setStatusCode(statusCode);
		return this;
	}
	
	public FuzResponseFactory setDescription(String description) {
		response.setStatusDescription(description);
		return this;
	}

	public FuzResponseFactory setBody(String body) {
		// TODO Auto-generated method stub
		return this;
	}

	public FuzResponseFactory setFailureReason(String failureReason) {
		response.setFailureReason(failureReason);
		return this;
	}
	
	public FuzResponse build() {
		return response;
	}
}
