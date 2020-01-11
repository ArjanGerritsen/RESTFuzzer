package nl.ou.se.fuzz.rest.service.shared;

import java.util.List;

public class ResponseDto {

	private List<String> violations;

	public ResponseDto(List<String> violations) {
		this.violations = violations;
	}

	public List<String> getViolations() {
		return violations;
	}

	public void setViolations(List<String> violations) {
		this.violations = violations;
	}	
}
