package nl.ou.se.rest.fuzzer.components.service.rmd.domain;

public class RmdActionDependencyDto {

    // variables
    private Long id;
    private Long actionId;
    private Long dependsOnActionId;

    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getActionId() {
        return actionId;
    }
    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }
    public Long getDependsOnActionId() {
        return dependsOnActionId;
    }
    public void setDependsOnActionId(Long dependsOnActionId) {
        this.dependsOnActionId = dependsOnActionId;
    }
}