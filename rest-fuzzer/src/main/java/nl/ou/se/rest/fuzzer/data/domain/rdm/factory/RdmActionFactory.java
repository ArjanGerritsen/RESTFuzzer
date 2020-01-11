package nl.ou.se.rest.fuzzer.data.domain.rdm.factory;

import nl.ou.se.rest.fuzzer.data.domain.rdm.RdmAction;

public class RdmActionFactory {

    private RdmAction rdmAction;
    private RdmParameterFactory rdmParameterFactory = new RdmParameterFactory();

    public RdmActionFactory create(String path, String httpMethod) {
        rdmAction = new RdmAction(path, httpMethod);
        return this;
    }

    public RdmActionFactory addParameter(String name, String context, String type, boolean required,
            String description) {
        int position = rdmAction.getParameters().size() + 1;
        rdmAction.getParameters().add(rdmParameterFactory.create(position, name, required, description, type, context).build());       
        return this;
    }

    public RdmAction build() {
        return rdmAction;
    }
}