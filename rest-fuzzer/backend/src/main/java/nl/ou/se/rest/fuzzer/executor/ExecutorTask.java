package nl.ou.se.rest.fuzzer.executor;

import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.task.TaskExecution;
import nl.ou.se.rest.fuzzer.task.TaskExecutionBase;

@Service
public class ExecutorTask extends TaskExecutionBase implements TaskExecution {

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(3 * 60 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
