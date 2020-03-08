package nl.ou.se.rest.fuzzer.components.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.task.dao.TaskService;
import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;
import nl.ou.se.rest.fuzzer.components.shared.Constants;

@Service
public class TasksSchedular {

    // variables
	@Autowired
	private TasksExecutor executor;

	@Autowired 
	private TaskService taskService;
	
	@Autowired
	private TaskExecutionFactory taskExecutionFactory;

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	// methods
	@Scheduled(cron = "*/20 * * * * *")
	public void runJobs() {
	    List<Task> tasksToRun = taskService.findQueued();
		logger.info(String.format(Constants.INFO_TASK_SCHEDULAR_START, tasksToRun.size()));

		tasksToRun.forEach(t -> executeTask(t));
	}

	private void executeTask(Task task) {
	    TaskExecution execution = taskExecutionFactory.create(task.getCanonicalName()).setMetaDataTuples(task.getMetaDataTuples()).build();
	    if (execution == null) {
	        logger.warn(String.format(Constants.WARN_TASK_SCHEDULAR_TASK_NOT_STARTED, task.getCanonicalName(), task.getId()));
	        return;
	    }

        logger.info(String.format(Constants.INFO_TASK_SCHEDULAR_START_TASK, task.getCanonicalName(), task.getId()));

	    executor.run(task, execution);
	}
}