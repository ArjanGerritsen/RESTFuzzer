package nl.ou.se.rest.fuzzer.components.data.rmd.factory;

import java.time.LocalDateTime;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.DiscoveryModus;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdActionDependency;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdParameter;

public class RmdActionDependencyFactory {

    // variables
    private RmdActionDependency actionDependency;

    // constructor
    public RmdActionDependencyFactory create(DiscoveryModus discoveryModus, RmdAction action, RmdParameter parameter,
            RmdAction actionDependsOn) {
        actionDependency = new RmdActionDependency(discoveryModus, action, parameter, actionDependsOn);
        actionDependency.setCreatedAt(LocalDateTime.now());
        return this;
    }

    // methods
    public RmdActionDependency build() {
        return actionDependency;
    }
}