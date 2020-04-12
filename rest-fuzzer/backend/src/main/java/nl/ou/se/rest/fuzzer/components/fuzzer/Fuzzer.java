package nl.ou.se.rest.fuzzer.components.fuzzer;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;

public interface Fuzzer {

    public void start(FuzProject project, Task task);

    public Boolean isMetaDataValid();

}