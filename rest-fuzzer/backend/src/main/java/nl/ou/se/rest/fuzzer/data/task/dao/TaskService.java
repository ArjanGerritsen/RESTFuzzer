package nl.ou.se.rest.fuzzer.data.task.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.data.task.domain.Task;

public interface TaskService extends CrudRepository<Task, Long> {

	@Query("SELECT t FROM tasks t WHERE t.startedAt IS NULL")
	List<Task> tasksNotStarted();
	
}