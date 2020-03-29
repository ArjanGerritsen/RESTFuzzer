package nl.ou.se.rest.fuzzer.components.data.rmd.factory;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdActionDependency;

public class RmdActionDependencyFactory {

    // variables
    private RmdActionDependency actionDependency;

    // constructor
    public RmdActionDependencyFactory create(RmdAction action, RmdAction actionDependsOn) {
        actionDependency = new RmdActionDependency(action, actionDependsOn);
        return this;
    }

    // methods
    public RmdActionDependency build() {
        return actionDependency;
    }
}