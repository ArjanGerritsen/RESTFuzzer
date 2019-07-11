package nl.ou.se.fuzz.rest.service.fuzzing.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OasParserJob implements Job {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public void run() {
		logger.info("test");
	}
}