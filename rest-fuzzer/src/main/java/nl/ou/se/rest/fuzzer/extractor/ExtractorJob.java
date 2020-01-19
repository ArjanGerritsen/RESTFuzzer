package nl.ou.se.rest.fuzzer.extractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import nl.ou.se.rest.fuzzer.data.rmd.dao.SutService;
import nl.ou.se.rest.fuzzer.data.rmd.domain.Sut;
import nl.ou.se.rest.fuzzer.job.JobExecution;

public class ExtractorJob implements JobExecution {

	@Autowired
	private SutService sutService;

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public void run() {
		logger.info("running ExtractorJob");
		
		// RmdProject project = new RmdProject("http://localhost/wordpress/rest-api/schema");
    	Sut sut = new Sut("/ws/git/ou-prototype/rest-fuzzer/src/main/resources/schema.json");
 
    	sutService.save(sut);
    	
        Extractor extractor = new Extractor(sut);
        extractor.processV2();

        sut.setHost(extractor.getHost());
        sut.setBasePath(extractor.getBasePath());
        sut.setActions(extractor.getActions());

        logger.info(sut.toString());
	}
}