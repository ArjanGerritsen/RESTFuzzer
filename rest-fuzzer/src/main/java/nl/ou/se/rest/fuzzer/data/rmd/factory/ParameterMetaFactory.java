package nl.ou.se.rest.fuzzer.data.rmd.factory;

import nl.ou.se.rest.fuzzer.data.rmd.domain.ParameterMeta;

public class ParameterMetaFactory {

    // variables
    private ParameterMeta parameterMeta;
    
    // methods
    public ParameterMetaFactory create(String key, Object value) {
        parameterMeta = new ParameterMeta(key, value);
        return this;
    }

    public ParameterMeta build() {
        return parameterMeta;
    }
}