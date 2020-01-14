package nl.ou.se.rest.fuzzer.data.domain.rdm.factory;

import nl.ou.se.rest.fuzzer.data.domain.rdm.RdmParameter;

public class RdmParameterFactory {

	// variables
    private RdmParameter rdmParameter;

    // methods
    public RdmParameterFactory create(int position, String name, boolean required, String description, String type,
            String context, String pattern, String format) {
        rdmParameter = new RdmParameter(position, name, required, description, type, context, pattern, format);
        return this;
    }

    public RdmParameter build() {
        return rdmParameter;
    }
}