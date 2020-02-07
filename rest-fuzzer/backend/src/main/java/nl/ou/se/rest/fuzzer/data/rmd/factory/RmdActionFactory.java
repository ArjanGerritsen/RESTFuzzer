package nl.ou.se.rest.fuzzer.data.rmd.factory;

import nl.ou.se.rest.fuzzer.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.data.rmd.domain.RmdParameter;
import nl.ou.se.rest.fuzzer.data.rmd.domain.RmdResponse;

public class RmdActionFactory {

    // variables
    private RmdAction action;

    // constructor
    public RmdActionFactory create(String url, String httpMethod) {
        action = new RmdAction(url, httpMethod);
        return this;
    }

    // methods
    public RmdActionFactory addParameter(RmdParameter parameter) {
        parameter.setPosition(action.getParameters().size() + 1);
        action.addParameter(parameter);
        return this;
    }

    public RmdActionFactory addResponse(RmdResponse response) {
        action.addResponse(response); 
        return this;
    }

    public RmdAction build() {
        return action;
    }
}