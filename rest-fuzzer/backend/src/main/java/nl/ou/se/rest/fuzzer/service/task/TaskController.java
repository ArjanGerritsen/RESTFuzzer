package nl.ou.se.rest.fuzzer.service.task;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nl.ou.se.rest.fuzzer.data.task.dao.TaskService;
import nl.ou.se.rest.fuzzer.data.task.domain.Task;
import nl.ou.se.rest.fuzzer.extractor.ExtractorTask;

@RestController()
@RequestMapping("/rest/tasks")
public class TaskController {

	private static final String EXTRACTOR = "extractor";
	
    @Autowired
	TaskService taskSerivce;

    @RequestMapping(path = "status/queued", method = RequestMethod.GET)
    public @ResponseBody List<TaskDto> findAllQueud() {
        List<Task> tasks = taskSerivce.findQueued();
        return TaskMapper.toDtos(tasks);
    }

    @RequestMapping(path = "status/running", method = RequestMethod.GET)
    public @ResponseBody List<TaskDto> findAllRunning() {
        List<Task> tasks = taskSerivce.findRunning();
        return TaskMapper.toDtos(tasks);
    }
    
    @RequestMapping(path = "status/completed", method = RequestMethod.GET)
    public @ResponseBody List<TaskDto> findAllCompleted() {
        List<Task> tasks = taskSerivce.findCompleted();
        return TaskMapper.toDtos(tasks);
    }

    @RequestMapping(path = "/{name}/start", method = RequestMethod.POST)
    public @ResponseBody TaskDto addExtractorTask(@PathVariable(value = "name") String name, @RequestBody Map<String, Object> metaDataTuples) {
        Task task = null;

        switch (name) {
        case EXTRACTOR:
            task = new Task(ExtractorTask.class.getCanonicalName());           
            break;
        default:
            break;
        }

        task.setMetaDataTuples(metaDataTuples);
        taskSerivce.save(task);
        return TaskMapper.toDto(task);
    }
}