package nl.ou.se.rest.fuzzer.data.domain.rdm;

import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RdmAction {

	// variables
    private String url;
    private RdmHttpMethod httpMethod;
    private SortedSet<RdmParameter> parameters = new TreeSet<>();
    private SortedSet<RdmResponse> responses = new TreeSet<>();

    // constructor
    public RdmAction(String url, String httpMethod) {
        this.url = url;
        this.httpMethod = RdmHttpMethod.valueOf(httpMethod);
    }

    // getters and setters
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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