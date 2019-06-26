package nl.ou.se.fuzz.rest.service.controller.rest.administrative.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/rest/administrative/tasks")
public class TasksController {

	private List<TaskDto> tasks = new ArrayList<TaskDto>();

	private TasksController() {
		this.init();
	}
	
	private void init() {
		TaskDto task = null;

		task = new TaskDto();
		task.setCreatedAt(new Date().getTime());
		task.setDescription("Fill (initial) test data.");
		task.setId(1);
		task.setLastStartedAt(null);
		task.setPayload("This is the payload ... gonna be big.");
		task.setStatus("NEW");
		task.setType("QRY");
		this.tasks.add(task);
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<TaskDto> list() {	
		return this.tasks;
	}
	
}
