package nl.ou.se.rest.fuzzer.components.fuzzer.type;

import java.util.Map;

import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;
import nl.ou.se.rest.fuzzer.components.fuzzer.metadata.MetaDataUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.metadata.MetaDataUtil.Meta;

@Service
public class FuzzerModelBasedDictionary extends FuzzerBase implements Fuzzer {

    // variable(s)
    private FuzProject project = null;
    private MetaDataUtil metaDataUtil = null;

    // constructor(s)
    
    // method(s)
    public void start(FuzProject project, Task task) {
        this.project = project;

    }

    public Boolean isMetaDataValid(Map<String, Object> metaDataTuples) {
        this.metaDataUtil = new MetaDataUtil(metaDataTuples);
        return metaDataUtil.isValid(Meta.CONFIGURATION);
    }
}