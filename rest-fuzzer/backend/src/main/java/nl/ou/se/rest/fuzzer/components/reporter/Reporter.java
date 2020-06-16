package nl.ou.se.rest.fuzzer.components.reporter;

import java.util.Map;

import nl.ou.se.rest.fuzzer.components.data.report.domain.Report;
import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;

public interface Reporter {

    public void start(Report report, Task task);

    public Boolean isMetaDataValid(Map<String, Object> metaDataTuples);

}