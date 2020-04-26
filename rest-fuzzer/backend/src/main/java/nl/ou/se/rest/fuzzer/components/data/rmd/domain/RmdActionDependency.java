package nl.ou.se.rest.fuzzer.components.data.rmd.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity(name = "rmd_actions_dependencies")
public class RmdActionDependency implements Comparable<RmdActionDependency> {

    // variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RmdParameter parameter;

    @ManyToOne(optional = false)
    @JoinColumn(name = "action_id")
    private RmdAction action;

    @ManyToOne(optional = false)
    @JoinColumn(name = "action_depends_on_id")
    private RmdAction actionDependsOn;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DiscoveryType discoveryType;

    // constructors
    public RmdActionDependency() {
    }

    public RmdActionDependency(RmdAction action, RmdParameter parameter, RmdAction actionDependsOn) {
        this.action = action;
        this.parameter = parameter;
        this.actionDependsOn = actionDependsOn;
    }

    // methods
    public int compareTo(RmdActionDependency other) {
        int compareAction = this.getAction().compareTo(other.getAction());
        if (compareAction != 0) {
            return compareAction;
        }

        int compareParameter = this.getParameter().compareTo(other.getParameter());
        if (compareParameter != 0) {
            return compareParameter;
        }

        return this.getActionDependsOn().compareTo(other.getActionDependsOn());
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RmdParameter getParameter() {
        return parameter;
    }

    public void setParameter(RmdParameter parameter) {
        this.parameter = parameter;
    }

    public RmdAction getAction() {
        return action;
    }

    public void setAction(RmdAction action) {
        this.action = action;
    }

    public RmdAction getActionDependsOn() {
        return actionDependsOn;
    }

    public void setActionDependsOn(RmdAction actionDependsOn) {
        this.actionDependsOn = actionDependsOn;
    }

    public DiscoveryType getDiscoveryType() {
        return discoveryType;
    }

    public void setDiscoveryType(DiscoveryType discoveryType) {
        this.discoveryType = discoveryType;
    }

    // toString
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}