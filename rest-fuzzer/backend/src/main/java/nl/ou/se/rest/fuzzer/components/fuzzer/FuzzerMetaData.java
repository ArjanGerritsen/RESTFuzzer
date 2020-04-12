package nl.ou.se.rest.fuzzer.components.fuzzer;

import java.util.List;

import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;

@Service
public class FuzzerMetaData {

    // configuration
    private static final String CONFIG_INCLUDE_ACTIONS = "";
    private static final String CONFIG_EXCLUDE_ACTIONS = "";
    private static final String CONFIG_EXCLUDE_PARAMETERS = "";

    // other, specific per fuzzer
    private static final String REPETITIONS = "";

    private String metaData;

    // constructor
    public FuzzerMetaData() {
    }

    // methods
    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }
    
    public Boolean isValid() {
        return true; // TODO
    }
    
    public List<RmdAction> getFilteredActions(List<RmdAction> actions) {
        return null; // TODO
    }
}