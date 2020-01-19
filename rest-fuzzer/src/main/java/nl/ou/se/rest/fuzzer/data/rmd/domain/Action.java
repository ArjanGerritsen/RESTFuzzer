package nl.ou.se.rest.fuzzer.data.rmd.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity(name = "rmd_actions")
public class Action {

	// variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
    private String path;

	@NotNull
	@Enumerated(EnumType.STRING)
    private HttpMethod httpMethod;

	@OneToMany
	@OrderBy("position ASC")
    private SortedSet<Parameter> parameters = new TreeSet<>();
	
	@OneToMany
	@OrderBy("statusCode ASC")
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