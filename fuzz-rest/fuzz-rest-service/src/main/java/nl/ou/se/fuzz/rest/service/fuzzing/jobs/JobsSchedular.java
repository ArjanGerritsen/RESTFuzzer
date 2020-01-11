package nl.ou.se.fuzz.rest.service.fuzzing.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JobsSchedular {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private JobsExecutor jobsExecutor;

	@Scheduled(fixedDelay = 10000)
	public void runJobs() {
		logger.info("Checking for jobs ...");
		jobsExecutor.execute(new OasParserJob());
	}	
}