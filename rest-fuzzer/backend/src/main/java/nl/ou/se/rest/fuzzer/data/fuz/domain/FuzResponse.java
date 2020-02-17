package nl.ou.se.rest.fuzzer.data.fuz.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity(name = "fuz_responses")
public class FuzResponse implements Comparable<FuzResponse> {

    // variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int statusCode;

    @Column
    private String statusDescription;

    @Column
    private String body;

    @Column
    private String failureReason;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id")
    @NotNull
    private FuzProject project;

    @ManyToOne(optional = false)
    @JoinColumn(name = "request_id")
    @NotNull
    private FuzRequest request;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime recievedAt;

    // methods
    public int compareTo(FuzResponse other) {
        return new String(this.getRequest().getPath() + this.getRequest().getHttpMethod())
                .compareTo(new String(other.getRequest().getPath() + other.getRequest().getHttpMethod()));

    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public FuzProject getProject() {
        return project;
    }

    public void setProject(FuzProject project) {
        this.project = project;
    }

    public FuzRequest getRequest() {
        return request;
    }

    public void setRequest(FuzRequest request) {
        this.request = request;
    }

    public LocalDateTime getRecievedAt() {
        return recievedAt;
    }

    public void setRecievedAt(LocalDateTime recievedAt) {
        this.recievedAt = recievedAt;
    }

    // toString
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
