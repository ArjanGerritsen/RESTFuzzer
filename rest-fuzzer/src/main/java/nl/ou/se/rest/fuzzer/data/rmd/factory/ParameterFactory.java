package nl.ou.se.rest.fuzzer.data.rmd.factory;

import nl.ou.se.rest.fuzzer.data.rmd.domain.Parameter;
import nl.ou.se.rest.fuzzer.data.rmd.domain.ParameterMeta;

public class ParameterFactory {

	// variables
    private Parameter parameter;

    // methods
    public ParameterFactory create(String name, boolean required, String description, String type,
            String context) {
        parameter = new Parameter(-1, name, required, description, type.toUpperCase(), context.toUpperCase());
        return this;
    }

    public ParameterFactory addMeta(ParameterMeta parameterMeta) {
        parameter.getMetas().add(parameterMeta);
        return this;
    }

    public Parameter build() {
        return parameter;
    }
}