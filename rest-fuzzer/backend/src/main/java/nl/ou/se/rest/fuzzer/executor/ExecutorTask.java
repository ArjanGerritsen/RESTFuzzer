package nl.ou.se.rest.fuzzer.executor;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.Constants;
import nl.ou.se.rest.fuzzer.data.fuz.dao.FuzProjectService;
import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.task.TaskExecution;
import nl.ou.se.rest.fuzzer.task.TaskExecutionBase;

@Service
public class ExecutorTask extends TaskExecutionBase implements TaskExecution {

	// variables
	public static final String KEY_PROJECT_ID = "project_id";

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private FuzProjectService projectService;

	@Autowired
	private Executor executor;

	@Override
	public void execute() {
		logger.info(String.format(Constants.INFO_TASK_START, this.getClass().getName()));

		if (this.getMetaDataValue(KEY_PROJECT_ID) == null) {
			logger.warn(String.format(Constants.WARN_TASK_VALUE_FOR_KEY_NOT_Present, this.getClass().getName(),
					KEY_PROJECT_ID));
			return;
		}

		Long projectId = Long.valueOf((Integer) this.getMetaDataValue(KEY_PROJECT_ID));
		Optional<FuzProject> oProject = projectService.findByIdWithRelations(projectId);

		if (!oProject.isPresent()) {
			logger.warn(String.format(Constants.WARN_TASK_SUT_NOT_FOUND, this.getClass().getName(), projectId));
			return;
		}

		FuzProject project = oProject.get();

		executor.start(project);
		
		project.setResponses(executor.getResponses());
		
		projectService.save(project);

		logger.info(String.format(Constants.INFO_TASK_STOP, this.getClass().getName()));
	}


}
