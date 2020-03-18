package nl.ou.se.rest.fuzzer.components.task;

import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;

public class TaskExecutionBase {

	// variables
    private Task task;

    // getters and setters
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}