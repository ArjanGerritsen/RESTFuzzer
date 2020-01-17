package nl.ou.se.rest.fuzzer.data.rmd.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Response implements Comparable<Response> {

	// variables
	Integer statusCode;
	String description;

	// constructor
	public Response(Integer statusCode, String description) {
		this.statusCode = statusCode;
		this.description = description;
	}
	
	// methods
	public int compareTo(Response other) {
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