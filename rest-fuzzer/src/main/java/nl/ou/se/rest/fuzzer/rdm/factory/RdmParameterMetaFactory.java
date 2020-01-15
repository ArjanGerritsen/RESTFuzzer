package nl.ou.se.rest.fuzzer.rdm.factory;

import nl.ou.se.rest.fuzzer.rdm.domain.RdmParameterMeta;

public class RdmParameterMetaFactory {

    // variables
    private RdmParameterMeta rdmParameterMeta;
    
    // methods
    public RdmParameterMetaFactory create(String key, Object value) {
        rdmParameterMeta = new RdmParameterMeta(key, value);
        return this;
    }

    public RdmParameterMeta build() {
        return rdmParameterMeta;
    }
}