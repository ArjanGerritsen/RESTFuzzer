package nl.ou.se.rest.fuzzer.executor;

import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzResponse;

@Service
public class Executor {

	// variables
	private ExecutorUtil executorUtil;
	
	// methods
	public void start(FuzProject project) {
		executorUtil = ExecutorUtil.getInstance();
		project.getRequests().forEach(r -> processRequest(r));
		executorUtil.destroy();
	}

	private void processRequest(FuzRequest request) {
		FuzResponse response = executorUtil.processRequest(request);
	}
}