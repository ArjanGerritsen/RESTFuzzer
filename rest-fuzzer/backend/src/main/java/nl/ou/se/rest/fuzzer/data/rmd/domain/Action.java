package nl.ou.se.rest.fuzzer.data.rmd.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.SortNatural;

@Entity(name = "rmd_actions")
public class Action implements Comparable<Action> {

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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "action_id")
    @SortNatural
    private SortedSet<Parameter> parameters = new TreeSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "action_id")
    @SortNatural
    private SortedSet<Response> responses = new TreeSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "sut_id")
    private Sut sut;

    // constructors
    public Action() {
    }

    public Action(String path, String httpMethod) {
        this.path = path;
        this.httpMethod = HttpMethod.valueOf(httpMethod);
    }

    // methods
    public int compareTo(Action other) {
        int pathCompare = this.getPath().compareTo(other.getPath());
        if (pathCompare != 0) {
            return pathCompare;
        }
        return this.getHttpMethod().compareTo(other.getHttpMethod());
    }

    public void addParameter(Parameter parameter) {
        parameter.setAction(this);
        this.getParameters().add(parameter);
    }

    public void addResponse(Response response) {
        response.setAction(this);
        this.getResponses().add(response);
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Sut getSut() {
        return sut;
    }

    public void setSut(Sut sut) {
        this.sut = sut;
    }

    // toString
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}