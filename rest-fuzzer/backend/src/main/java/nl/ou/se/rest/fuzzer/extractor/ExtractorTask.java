package nl.ou.se.rest.fuzzer.extractor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.Constants;
import nl.ou.se.rest.fuzzer.data.rmd.dao.RmdSutService;
import nl.ou.se.rest.fuzzer.data.rmd.domain.RmdSut;
import nl.ou.se.rest.fuzzer.task.TaskExecution;

@Service
public class ExtractorTask implements TaskExecution {

	public static final String KEY_SUT_ID = "sut_id";

	// variables
    private Map<String, Object> metaDataTuples = new HashMap<>();

	@Autowired
	private RmdSutService sutService;

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	// methods
	public void execute() {
		logger.info(Constants.INFO_EXTRACTOR_TASK_START);

		if (this.getMetaDataValue(KEY_SUT_ID) == null) {
            logger.warn(String.format(Constants.WARN_TASK_VALUE_FOR_KEY_NOT_Present, this.getClass().getName(), KEY_SUT_ID));
            return;		    
		}

		Long sutId = Long.valueOf((Integer) this.getMetaDataValue(KEY_SUT_ID));
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

        logger.info(Constants.INFO_EXTRACTOR_TASK_STOP);        
	}

    public void setMetaDataTuples(Map<String, Object> metaDataTuples) {
        this.metaDataTuples = metaDataTuples;
    }

    public Object getMetaDataValue(String key) {
        return this.metaDataTuples.get(key);
    }
}