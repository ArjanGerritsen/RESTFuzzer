package nl.ou.se.rest.fuzzer.rdm.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RdmAction {

	// variables
    private String path;
    private RdmHttpMethod httpMethod;
    private SortedSet<RdmParameter> parameters = new TreeSet<>();
    private SortedSet<RdmResponse> responses = new TreeSet<>();

    // constructor
    public RdmAction(String path, String httpMethod) {
        this.path = path;
        this.httpMethod = RdmHttpMethod.valueOf(httpMethod);
    }

    // getters and setters
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public RdmHttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(RdmHttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public SortedSet<RdmParameter> getParameters() {
		return parameters;
	}

	public void setParameters(SortedSet<RdmParameter> parameters) {
		this.parameters = parameters;
	}

	public SortedSet<RdmResponse> getResponses() {
		return responses;
	}

	public void setResponses(SortedSet<RdmResponse> responses) {
		this.responses = responses;
	}

	// toString
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);		
	}
}