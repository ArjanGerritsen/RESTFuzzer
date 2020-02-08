package nl.ou.se.rest.fuzzer.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzProject;

@Service
public class GeneratorSimple {

	// variables
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	// methods
	public void start(FuzProject project) {
		// logger.debug("size: " + project.getSut().getActions().size());
	}
}
