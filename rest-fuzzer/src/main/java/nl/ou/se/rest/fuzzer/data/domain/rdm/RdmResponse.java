package nl.ou.se.rest.fuzzer.data.domain.rdm;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RdmResponse implements Comparable<RdmResponse> {

	// variables
	Integer statusCode;
	String description;

	// constructor
	RdmResponse(Integer statusCode, String description) {
		this.statusCode = statusCode;
		this.description = description;
	}
	
	// methods
	public int compareTo(RdmResponse other) {
		return this.getStatusCode().compareTo(other.getStatusCode());
	}

	// getters and setters
	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// toString
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}