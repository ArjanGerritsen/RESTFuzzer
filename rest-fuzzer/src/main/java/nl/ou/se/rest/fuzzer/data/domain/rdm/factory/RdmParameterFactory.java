package nl.ou.se.rest.fuzzer.data.domain.rdm.factory;

import nl.ou.se.rest.fuzzer.data.domain.rdm.RdmParameter;

public class RdmParameterFactory {

    private RdmParameter rdmParameter;

    public RdmParameterFactory create(int position, String name, boolean required, String description, String type,
            String context) {
        rdmParameter = new RdmParameter(position, name, required, description, type, context);
        return this;
    }

    public RdmParameter build() {
        return rdmParameter;
    }
}