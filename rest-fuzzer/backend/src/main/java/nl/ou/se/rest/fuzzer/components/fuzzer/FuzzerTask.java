package nl.ou.se.rest.fuzzer.components.fuzzer;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzProjectService;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.components.shared.Constants;
import nl.ou.se.rest.fuzzer.components.task.TaskExecution;
import nl.ou.se.rest.fuzzer.components.task.TaskExecutionBase;

@Service
public class FuzzerTask extends TaskExecutionBase implements TaskExecution {

	// variables
	public static final String KEY_PROJECT_ID = "project_id";

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private FuzProjectService projectService;
	
	@Autowired
	private FuzzerBasic fuzzerBasic;

	@Override
	public void execute() {
		logger.info(String.format(Constants.INFO_TASK_START, this.getClass().getName()));

		if (!this.getTask().getMetaDataTuples().containsKey(KEY_PROJECT_ID)) {
			logger.warn(String.format(Constants.WARN_TASK_VALUE_FOR_KEY_NOT_Present, this.getClass().getName(),
					KEY_PROJECT_ID));
			return;
		}

		Long projectId = Long.valueOf((Integer) this.getTask().getMetaDataTuples().get(KEY_PROJECT_ID));
		Optional<FuzProject> oProject = projectService.findById(projectId);

		if (!oProject.isPresent()) {
			logger.warn(String.format(Constants.WARN_TASK_PROJECT_NOT_FOUND, this.getClass().getName(), projectId));
			return;
		}

		FuzProject project = oProject.get();

		switch (project.getType()) {
		case BASIC_FUZZER:
		    fuzzerBasic.start(project);
			break;
		default:
			break;
		}

		projectService.save(project);

		logger.info(String.format(Constants.INFO_TASK_STOP, this.getClass().getName()));
	}
}