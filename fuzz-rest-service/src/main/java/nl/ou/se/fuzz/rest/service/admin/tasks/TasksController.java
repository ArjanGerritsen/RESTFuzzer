package nl.ou.se.fuzz.rest.service.admin.tasks;

import java.util.Date;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nl.ou.se.fuzz.rest.service.admin.shared.EventDto;
import nl.ou.se.fuzz.rest.service.admin.shared.EventType;

@RestController()
@RequestMapping("/rest/admin/tasks")
public class TasksController {

	private SortedSet<TaskDto> tasks = new TreeSet<>();
	private SortedSet<EventDto> events = new TreeSet<>();

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
		task.setStatus(TaskStatus.NEW);
		task.setType(TaskType.DATABASE);
		this.tasks.add(task);

		task = new TaskDto();
		task.setCreatedAt(new Date().getTime() + 3600000);
		task.setDescription("Truncate projects.");
		task.setId(2);
		task.setLastStartedAt(null);
		task.setPayload("This is the payload ... gonna be big.");
		task.setStatus(TaskStatus.RUNNING);
		task.setType(TaskType.DATABASE);
		this.tasks.add(task);

		task = new TaskDto();
		task.setCreatedAt(new Date().getTime() + 7200000);
		task.setDescription("Truncate events.");
		task.setId(3);
		task.setLastStartedAt(null);
		task.setPayload("This is the payload ... gonna be big.");
		task.setStatus(TaskStatus.FAIL);
		task.setType(TaskType.MULTI_EXEC);
		this.tasks.add(task);
				
		EventDto event = null;
		
		event = new EventDto();
		event.setContent("Taak gestart.");
		event.setCreatedAt(new Date().getTime());
		event.setId(1);
		event.setType(EventType.INFO);
		this.events.add(event);

		event = new EventDto();
		event.setContent("Taak gestopt.");
		event.setCreatedAt(new Date().getTime()+900);
		event.setId(2);
		event.setType(EventType.INFO);
		this.events.add(event);

		event = new EventDto();
		event.setContent("Oops, iets kleins ging mis.");
		event.setCreatedAt(new Date().getTime()+1800);
		event.setId(3);
		event.setType(EventType.WARN);
		this.events.add(event);

		event = new EventDto();
		event.setContent("Ernstige fout opgetreden.");
		event.setCreatedAt(new Date().getTime()+3600);
		event.setId(4);
		event.setType(EventType.ERROR);
		this.events.add(event);		
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Set<TaskDto> listTasks() {
		return this.tasks;
	}

	@RequestMapping(path="/{taskId}/events", method = RequestMethod.GET)
	public @ResponseBody Set<EventDto> listEvents(@PathVariable Integer taskId) {
		if (taskId == 1) {
			return this.events.stream().filter(e -> e.getType().equals(EventType.INFO)).collect(Collectors.toSet());
		} else if (taskId == 2) {
			return this.events.stream().filter(e -> !e.getType().equals(EventType.INFO)).collect(Collectors.toSet());
		}
		return this.events;
	}

	@RequestMapping(path="/{taskId}/execute", method = RequestMethod.POST)
	public @ResponseBody TaskDto execute(@PathVariable Integer taskId) {
		return new TaskDto();
	}

	@RequestMapping(path="/{taskId}/execute", method = RequestMethod.DELETE)
	public @ResponseBody TaskDto delete(@PathVariable Integer taskId) {
		return new TaskDto();
	}
}