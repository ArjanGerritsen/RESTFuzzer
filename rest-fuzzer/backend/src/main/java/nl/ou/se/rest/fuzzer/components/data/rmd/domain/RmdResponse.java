package nl.ou.se.rest.fuzzer.components.data.rmd.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity(name = "rmd_responses")
public class RmdResponse implements Comparable<RmdResponse> {

    // variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer statusCode;

    @NotNull
    @NotEmpty
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "action_id")
    private RmdAction action;

    // constructor
    public RmdResponse() {
    }

    public RmdResponse(Integer statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    // methods
    public int compareTo(RmdResponse other) {
        if (this.getAction() != null && other.getAction() != null) {
            int actionCompare = this.getAction().compareTo(other.getAction());
            if (actionCompare != 0) {
                return actionCompare;
            }
        }

        return this.getStatusCode().compareTo(other.getStatusCode());
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public RmdAction getAction() {
        return action;
    }

    public void setAction(RmdAction action) {
        this.action = action;
    }

    // toString
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}