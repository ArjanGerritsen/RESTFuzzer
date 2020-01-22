package nl.ou.se.rest.fuzzer.data.rmd.factory;

import java.util.Map;

import nl.ou.se.rest.fuzzer.data.rmd.domain.Parameter;

public class ParameterFactory {

	// variables
    private Parameter parameter;

    // methods
    public ParameterFactory create(String name, boolean required, String description, String type,
            String context) {
        parameter = new Parameter(-1, name, required, description, type.toUpperCase(), context.toUpperCase());
        return this;
    }

    public ParameterFactory setMetaDataTuples(Map<String, Object> metaDataTuples) {
        parameter.setMetaDataTuples(metaDataTuples);
        return this;
    }
    public Parameter build() {
        return parameter;
    }
}