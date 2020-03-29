package nl.ou.se.rest.fuzzer.components.extractor;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.rmd.dao.RmdActionDependencyService;
import nl.ou.se.rest.fuzzer.components.data.rmd.dao.RmdSutService;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdActionDependency;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdSut;
import nl.ou.se.rest.fuzzer.components.task.TaskExecution;
import nl.ou.se.rest.fuzzer.components.task.TaskExecutionBase;

@Service
public class ExtractorTask extends TaskExecutionBase implements TaskExecution {

    // variables
    public static final String KEY_SUT_ID = "sut_id";

    @Autowired
    private RmdSutService sutService;
    
    @Autowired
    private RmdActionDependencyService actionDependencyService;

    // methods
    public void execute() {
    	this.logStart(ExtractorTask.class.getTypeName());

    	Object objSutId = this.getValueForKey(ExtractorTask.class, KEY_SUT_ID);
    	if (objSutId == null) {
    		return;
    	}

		Long sutId = Long.valueOf((Integer) objSutId);
    	Optional<RmdSut> oSut = sutService.findById(sutId);
    	
    	if (!this.isOptionalPresent(ExtractorTask.class, oSut, sutId)) {
    	    return;
    	}

    	RmdSut sut = oSut.get();

    	// REST model extraction
        Extractor extractor = new Extractor(sut);
        extractor.processV2();

        sut.setTitle(extractor.getTitle());
        sut.setDescription(extractor.getDescription());
        sut.setHost(extractor.getHost());
        sut.setBasePath(extractor.getBasePath());
        extractor.getActions().forEach(a -> sut.addAction(a));

        sutService.save(sut);

        // dependencies between actions
        DependencyFinder dependencyFinder = new DependencyFinder(sut);
        dependencyFinder.process();
        Set<RmdActionDependency> actionDependencies = dependencyFinder.getActionDepencies();

        actionDependencyService.saveAll(actionDependencies);

    	this.logStop(ExtractorTask.class.getTypeName());
	}
}