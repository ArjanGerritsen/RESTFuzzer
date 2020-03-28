package nl.ou.se.rest.fuzzer.components.data.rmd.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity(name = "rmd_actions_dependencies")
public class RmdActionDependency {

    // variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RmdAction action;

    @ManyToOne
    private RmdAction actionDependsOn;

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    // toString
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}