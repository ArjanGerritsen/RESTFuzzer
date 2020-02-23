package nl.ou.se.rest.fuzzer.data.fuz.domain;

import java.time.LocalDateTime;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import nl.ou.se.rest.fuzzer.JsonUtil;
import nl.ou.se.rest.fuzzer.data.rmd.domain.HttpMethod;
import nl.ou.se.rest.fuzzer.data.rmd.domain.ParameterContext;

@Entity(name = "fuz_requests")
public class FuzRequest implements Comparable<FuzRequest> {

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

    private String formdataParametersJson;
    private String headerParametersJson;
    private String pathParametersJson;
    private String queryParametersJson;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id")
    private FuzProject project;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    // constructors
    public FuzRequest() {
    }

    public FuzRequest(String path, String httpMethod) {
        this.path = path;
        this.httpMethod = HttpMethod.valueOf(httpMethod);
    }

    // methods
    public int compareTo(FuzRequest other) {
        return new String(this.getPath() + this.getHttpMethod())
                .compareTo(new String(other.getPath() + other.getHttpMethod()));
    }

    public Map<String, Object> getParameterMap(ParameterContext context) {
        String json = null;

        switch (context) {
        case FORMDATA:
            json = this.formdataParametersJson;
            break;
        case HEADER:
            json = this.headerParametersJson;
            break;
        case PATH:
            json = this.pathParametersJson;
            break;
        case QUERY:
            json = this.queryParametersJson;
            break;
        default:
            break;
        }
        return JsonUtil.stringToMap(json);
    }

    public void setParameterMap(ParameterContext context, Map<String, Object> parameters) {
        String json = JsonUtil.mapToString(parameters);

        switch (context) {
        case FORMDATA:
            this.formdataParametersJson = json;
            break;
        case HEADER:
            this.headerParametersJson = json;
            break;
        case PATH:
            this.pathParametersJson = json;
            break;
        case QUERY:
            this.queryParametersJson = json;
            break;
        default:
            break;
        }
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

    public String getFormdataParametersJson() {
        return formdataParametersJson;
    }

    public void setFormdataParametersJson(String formdataParametersJson) {
        this.formdataParametersJson = formdataParametersJson;
    }

    public String getHeaderParametersJson() {
        return headerParametersJson;
    }

    public void setHeaderParametersJson(String headerParametersJson) {
        this.headerParametersJson = headerParametersJson;
    }

    public String getPathParametersJson() {
        return pathParametersJson;
    }

    public void setPathParametersJson(String pathParametersJson) {
        this.pathParametersJson = pathParametersJson;
    }

    public String getQueryParametersJson() {
        return queryParametersJson;
    }

    public void setQueryParametersJson(String queryParametersJson) {
        this.queryParametersJson = queryParametersJson;
    }

    public FuzProject getProject() {
        return project;
    }

    public void setProject(FuzProject project) {
        this.project = project;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // toString
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
