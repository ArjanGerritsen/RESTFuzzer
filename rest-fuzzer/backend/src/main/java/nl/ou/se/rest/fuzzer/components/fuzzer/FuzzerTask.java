package nl.ou.se.rest.fuzzer.components.fuzzer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzProjectService;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.components.task.TaskExecution;
import nl.ou.se.rest.fuzzer.components.task.TaskExecutionBase;

@Service
public class FuzzerTask extends TaskExecutionBase implements TaskExecution {

	// variables
	public static final String KEY_PROJECT_ID = "project_id";

	@Autowired
	private FuzProjectService projectService;
	
	@Autowired
	private FuzzerBasic fuzzerBasic;

	@Override
	public void execute() {
    	this.logStart(FuzzerTask.class.getTypeName());

    	Object objProjectId = this.getValueForKey(FuzzerTask.class, KEY_PROJECT_ID);
    	if (objProjectId == null) {
    		return;
    	}

		Long projectId = Long.valueOf((Integer) objProjectId);
		Optional<FuzProject> oProject = projectService.findById(projectId);
    	
    	if (!this.isOptionalPresent(FuzzerTask.class, oProject, projectId)) {
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

    	this.logStop(FuzzerTask.class.getTypeName());
	}
}