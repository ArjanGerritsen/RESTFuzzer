package nl.ou.se.rest.fuzzer.data.rmd.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Action {

	// variables
    private String path;
    private HttpMethod httpMethod;
    private SortedSet<Parameter> parameters = new TreeSet<>();
    private SortedSet<Response> responses = new TreeSet<>();

    // constructors
    public Action(String path, String httpMethod) {
        this.path = path;
        this.httpMethod = HttpMethod.valueOf(httpMethod);
    }

    // getters and setters
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public SortedSet<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(SortedSet<Parameter> parameters) {
		this.parameters = parameters;
	}

	public SortedSet<Response> getResponses() {
		return responses;
	}

	public void setResponses(SortedSet<Response> responses) {
		this.responses = responses;
	}

	// toString
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);		
	}
}