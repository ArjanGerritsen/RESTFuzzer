package nl.ou.se.rest.fuzzer.components.extractor;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.rmd.dao.RmdSutService;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdSut;
import nl.ou.se.rest.fuzzer.components.shared.Constants;
import nl.ou.se.rest.fuzzer.components.task.TaskExecution;
import nl.ou.se.rest.fuzzer.components.task.TaskExecutionBase;

@Service
public class ExtractorTask extends TaskExecutionBase implements TaskExecution {

    // variables
    public static final String KEY_SUT_ID = "sut_id";

    @Autowired
    private RmdSutService sutService;

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    // methods
    public void execute() {
		logger.info(String.format(Constants.INFO_TASK_START, this.getClass().getName()));

		if (!this.getTask().getMetaDataTuples().containsKey(KEY_SUT_ID)) {
            logger.warn(String.format(Constants.WARN_TASK_VALUE_FOR_KEY_NOT_Present, this.getClass().getName(), KEY_SUT_ID));
            return;		    
		}

		Long sutId = Long.valueOf((Integer) this.getTask().getMetaDataTuples().get(KEY_SUT_ID));
    	Optional<RmdSut> oSut = sutService.findById(sutId);

    	if (!oSut.isPresent()) {
    	    logger.warn(String.format(Constants.WARN_TASK_SUT_NOT_FOUND, this.getClass().getName(), sutId));
    	    return;
    	}

    	RmdSut sut = oSut.get();

        Extractor extractor = new Extractor(sut);
        extractor.processV2();

        sut.setTitle(extractor.getTitle());
        sut.setDescription(extractor.getDescription());
        sut.setHost(extractor.getHost());
        sut.setBasePath(extractor.getBasePath());
        extractor.getActions().forEach(a -> sut.addAction(a));

        sutService.save(sut);

		logger.info(String.format(Constants.INFO_TASK_STOP, this.getClass().getName()));
	}
}