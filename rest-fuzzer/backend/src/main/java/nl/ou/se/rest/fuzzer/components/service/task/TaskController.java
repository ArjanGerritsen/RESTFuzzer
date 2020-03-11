package nl.ou.se.rest.fuzzer.components.service.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.ou.se.rest.fuzzer.components.data.task.dao.TaskService;
import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;
import nl.ou.se.rest.fuzzer.components.extractor.ExtractorTask;
import nl.ou.se.rest.fuzzer.components.fuzzer.FuzzerTask;
import nl.ou.se.rest.fuzzer.components.service.HttpResponseDto;

@RestController()
@RequestMapping("/rest/tasks")
public class TaskController {

	// variables
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    private static final String EXTRACTOR = "extractor";
    private static final String FUZZER = "fuzzer";
    private static final int MAX_TASKS_ENDED = 10;

    @Autowired
	TaskService taskSerivce;

    // methods
    @RequestMapping(path = "progress", method = RequestMethod.GET)
    public @ResponseBody List<TaskDto> findAllProgress() {
        List<Task> tasks = new ArrayList<>();

        tasks = taskSerivce.findQueued();
        tasks.addAll(taskSerivce.findRunning());
        tasks.addAll(taskSerivce.findEnded(PageRequest.of(0, MAX_TASKS_ENDED)));

        return TaskMapper.toDtos(tasks);
    }

    @RequestMapping(path = "running_or_queued/suts/{sut_id}/count", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> countRunningForSut(@PathVariable(value = "sut_id") Long sutId) {
        List<Task> tasks = taskSerivce.findQueuedAndRunning();
        Long count = tasks.stream().filter(t ->  t.isForSut(sutId)).count();

        return ResponseEntity.ok().body(count);
    }

    @RequestMapping(path = "running_or_queued/projects/{project_id}/count", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> countRunningForProject(@PathVariable(value = "project_id") Long projectId) {
        List<Task> tasks = taskSerivce.findQueuedAndRunning();
        Long count = tasks.stream().filter(t ->  t.isForProject(projectId)).count();

        return ResponseEntity.ok().body(count);
    }

    @RequestMapping(path = "/{name}/start", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> addExtractorTask(@PathVariable(value = "name") String name, @RequestBody Map<String, Object> metaDataTuples) {
        Task task = null;

        switch (name) {
        case EXTRACTOR:
            task = new Task(ExtractorTask.class.getCanonicalName());           
            break;
        case FUZZER:
            task = new Task(FuzzerTask.class.getCanonicalName());           
            break;
        default:
			String json = "";
			try {
				HttpResponseDto response = new HttpResponseDto("Unkown task name.");
				json = new ObjectMapper().writeValueAsString(response);
			} catch (JsonProcessingException e) {
				logger.warn(e.getMessage());
			}
        	return ResponseEntity.badRequest().body(json);
        }

        task.setMetaDataTuples(metaDataTuples);
        taskSerivce.save(task);
        return ResponseEntity.ok().body(TaskMapper.toDto(task));
    }
}