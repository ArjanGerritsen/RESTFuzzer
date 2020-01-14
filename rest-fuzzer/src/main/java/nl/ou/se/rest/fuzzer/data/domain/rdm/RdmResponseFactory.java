package nl.ou.se.rest.fuzzer.data.domain.rdm;

public class RdmResponseFactory {

	// variables
	private RdmResponse rdmResponse;
	
	// methods
	public RdmResponseFactory create(Integer statusCode, String description) {
		rdmResponse = new RdmResponse(statusCode, description);
		return this;
	}

	public RdmResponse build() {
		return this.rdmResponse;
	}
}
