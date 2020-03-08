package nl.ou.se.rest.fuzzer.components.service.task;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;

public abstract class TaskMapper {

	// methods
	public static List<TaskDto> toDtos(List<Task> tasks) {
		return tasks.stream().map(t -> TaskMapper.toDto(t)).collect(Collectors.toList());
	}

	public static TaskDto toDto(Task task) {
		TaskDto dto = new TaskDto();
		BeanUtils.copyProperties(task, dto);
		return dto;
	}
	
    public static Task toDomain(TaskDto dto) {
        Task task = new Task();
        BeanUtils.copyProperties(dto, task);
        return task;
    }
}