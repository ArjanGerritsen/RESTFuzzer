package nl.ou.se.rest.fuzzer.data.rmd.factory;

import nl.ou.se.rest.fuzzer.data.rmd.domain.Action;
import nl.ou.se.rest.fuzzer.data.rmd.domain.Parameter;

public class ActionFactory {

    // variables
    private Action action;
    private ResponseFactory responseFactory = new ResponseFactory();

    // constructor
    public ActionFactory create(String url, String httpMethod) {
        action = new Action(url, httpMethod);
        return this;
    }

    // methods
    public ActionFactory addParameter(Parameter parameter) {
        parameter.setPosition(action.getParameters().size() + 1);
        action.getParameters().add(parameter);
        return this;
    }

    public ActionFactory addResponse(String statusCode, String description) {
        action.getResponses().add(responseFactory.create(Integer.valueOf(statusCode), description).build());
        return this;
    }

    public Action build() {
        return action;
    }
}