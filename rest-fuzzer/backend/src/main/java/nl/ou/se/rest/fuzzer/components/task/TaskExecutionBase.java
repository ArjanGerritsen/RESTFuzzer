package nl.ou.se.rest.fuzzer.components.task;

import java.util.HashMap;
import java.util.Map;

public class TaskExecutionBase {

	// variables
    private Map<String, Object> metaDataTuples = new HashMap<>();

    // getters and setters
    public void setMetaDataTuples(Map<String, Object> metaDataTuples) {
        this.metaDataTuples = metaDataTuples;
    }

    public Object getMetaDataValue(String key) {
        return this.metaDataTuples.get(key);
    }
}