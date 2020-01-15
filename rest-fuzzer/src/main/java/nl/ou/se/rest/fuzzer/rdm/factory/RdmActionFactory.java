package nl.ou.se.rest.fuzzer.rdm.factory;

import nl.ou.se.rest.fuzzer.rdm.domain.RdmAction;
import nl.ou.se.rest.fuzzer.rdm.domain.RdmParameter;
import nl.ou.se.rest.fuzzer.rdm.domain.RdmResponseFactory;

public class RdmActionFactory {

    // variables
    private RdmAction rdmAction;
    private RdmResponseFactory rdmResponseFactory = new RdmResponseFactory();

    // constructor
    public RdmActionFactory create(String url, String httpMethod) {
        rdmAction = new RdmAction(url, httpMethod);
        return this;
    }

    // methods
    public RdmActionFactory addParameter(RdmParameter rdmParameter) {
        rdmParameter.setPosition(rdmAction.getParameters().size() + 1);
        rdmAction.getParameters().add(rdmParameter);
        return this;
    }

    public RdmActionFactory addResponse(String statusCode, String description) {
        rdmAction.getResponses().add(rdmResponseFactory.create(Integer.valueOf(statusCode), description).build());
        return this;
    }

    public RdmAction build() {
        return rdmAction;
    }
}