package nl.ou.se.rest.fuzzer.components.fuzzer.type;

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

    private Fuzzer fuzzer;

    @Autowired
    private FuzzerBasic fuzzerBasic;

    @Autowired
    private FuzzerModelBased fuzzerModelBased;

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
            fuzzer = fuzzerBasic;
            break;
        case MB_FUZZER:
            fuzzer = fuzzerModelBased;
            break;
        default:
            break;
        }

        if (fuzzer.isMetaDataValid(project.getMetaDataTuples())) {
            fuzzer.start(project, this.getTask());
        }

        projectService.save(project);

        this.logStop(FuzzerTask.class.getTypeName());
    }
}