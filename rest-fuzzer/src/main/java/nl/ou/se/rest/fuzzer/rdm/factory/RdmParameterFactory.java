package nl.ou.se.rest.fuzzer.rdm.factory;

import nl.ou.se.rest.fuzzer.rdm.domain.RdmParameter;
import nl.ou.se.rest.fuzzer.rdm.domain.RdmParameterMeta;

public class RdmParameterFactory {

	// variables
    private RdmParameter rdmParameter;

    // methods
    public RdmParameterFactory create(String name, boolean required, String description, String type,
            String context) {
        rdmParameter = new RdmParameter(-1, name, required, description, type.toUpperCase(), context.toUpperCase());
        return this;
    }

    public RdmParameterFactory addMeta(RdmParameterMeta rdmParameterMeta) {
        rdmParameter.getMetas().add(rdmParameterMeta);
        return this;
    }

    public RdmParameter build() {
        return rdmParameter;
    }
}