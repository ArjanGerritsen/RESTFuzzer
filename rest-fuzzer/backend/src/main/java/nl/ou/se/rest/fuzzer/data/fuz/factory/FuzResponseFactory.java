package nl.ou.se.rest.fuzzer.data.fuz.factory;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzResponse;

public class FuzResponseFactory {

	private FuzResponse response;

	public FuzResponseFactory create(FuzRequest request) {
		response = new FuzResponse();

		return this;
	}

	public FuzResponseFactory setCode(int statusCode) {
		// TODO Auto-generated method stub
		return this;
	}
	
	public FuzResponseFactory setDescription(String description) {
		// TODO Auto-generated method stub
		return this;
	}

	public void setBody(String body) {
		// TODO Auto-generated method stub
		
	}

	public FuzResponseFactory setFailureReason(String failureReason) {
		// TODO Auto-generated method stub
		return this;
	}
	
	public FuzResponse build() {
		return response;
	}
}
