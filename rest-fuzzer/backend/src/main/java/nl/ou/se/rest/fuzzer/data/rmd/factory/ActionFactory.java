package nl.ou.se.rest.fuzzer.data.rmd.factory;

import nl.ou.se.rest.fuzzer.data.rmd.domain.Action;
import nl.ou.se.rest.fuzzer.data.rmd.domain.Parameter;
import nl.ou.se.rest.fuzzer.data.rmd.domain.Response;

public class ActionFactory {

    // variables
    private Action action;

    // constructor
    public ActionFactory create(String url, String httpMethod) {
        action = new Action(url, httpMethod);
        return this;
    }

    // methods
    public ActionFactory addParameter(Parameter parameter) {
        parameter.setPosition(action.getParameters().size() + 1);
        action.addParameter(parameter);
        return this;
    }

    public ActionFactory addResponse(Response response) {
        action.addResponse(response); 
        return this;
    }

    public Action build() {
        return action;
    }
}