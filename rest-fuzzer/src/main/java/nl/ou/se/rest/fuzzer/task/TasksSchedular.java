package nl.ou.se.rest.fuzzer.task;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.data.task.dao.TaskService;
import nl.ou.se.rest.fuzzer.data.task.domain.Task;
import nl.ou.se.rest.fuzzer.extractor.ExtractorTask;

@Service
public class TasksSchedular {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private TasksExecutor executor;

	@Autowired 
	private TaskService taskService;

	@Scheduled(cron = "*/10 * * * * *")
	public void runJobs() {
		List<Task> tasksToRun = taskService.tasksNotStarted();
		logger.info(String.format("Starting %s task(s)", tasksToRun.size()));

		Task task = new Task(ExtractorTask.class.getCanonicalName());
		taskService.save(task);

		tasksToRun.forEach(t -> executeTask(t));
	}

	private void executeTask(Task task) {
		TaskExecution instance = null;

		try {
			Class<?> clazz = Class.forName(task.getCanonicalName());
			Constructor<?> constructor = clazz.getConstructor();
			instance = (TaskExecution) constructor.newInstance();
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO logging
			e.printStackTrace();
		}

		executor.run(task, instance);
	}
}