package nl.ou.se.rest.fuzzer.executor;

import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.data.fuz.domain.FuzResponse;
import nl.ou.se.rest.fuzzer.service.task.TaskController;

@Service
public class Executor {

	// variables
	private Logger logger = LoggerFactory.getLogger(TaskController.class);

	private ExecutorUtil executorUtil;
	private SortedSet<FuzResponse> responses = new TreeSet<>();

	// methods
	public void start(FuzProject project) {
		executorUtil = ExecutorUtil.getInstance();
		project.getRequests().forEach(r -> processRequest(r));
		executorUtil.destroy();
	}

	private void processRequest(FuzRequest request) {
		FuzResponse response = executorUtil.processRequest(request);
		logger.info("TODO: " + response.getFailureReason() + " / " + response.getStatusCode());
		this.responses.add(response);
	}

	// getters and setters
	public SortedSet<FuzResponse> getResponses() {
		return responses;
	}

	public void setResponses(SortedSet<FuzResponse> responses) {
		this.responses = responses;
	}
}
