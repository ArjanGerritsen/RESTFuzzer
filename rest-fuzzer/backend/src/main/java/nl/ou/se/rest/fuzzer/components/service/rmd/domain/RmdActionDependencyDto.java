package nl.ou.se.rest.fuzzer.components.service.rmd.domain;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.DiscoveryType;

public class RmdActionDependencyDto {

    // variables
    private Long id;
    private RmdActionDto action;
    private RmdParameterDto parameter;
    private RmdActionDto dependsOnAction;
    private DiscoveryType discoveryType;

    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public RmdActionDto getAction() {
        return action;
    }
    public void setAction(RmdActionDto action) {
        this.action = action;
    }
    public RmdParameterDto getParameter() {
        return parameter;
    }
    public void setParameter(RmdParameterDto parameter) {
        this.parameter = parameter;
    }
    public RmdActionDto getDependsOnAction() {
        return dependsOnAction;
    }
    public void setDependsOnAction(RmdActionDto dependsOnAction) {
        this.dependsOnAction = dependsOnAction;
    }
    public DiscoveryType getDiscoveryType() {
        return discoveryType;
    }
    public void setDiscoveryType(DiscoveryType discoveryType) {
        this.discoveryType = discoveryType;
    }
}