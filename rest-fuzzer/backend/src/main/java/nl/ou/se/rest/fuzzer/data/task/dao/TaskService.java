package nl.ou.se.rest.fuzzer.data.task.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.data.task.domain.Task;

public interface TaskService extends CrudRepository<Task, Long> {

	@Query("SELECT t FROM tasks t WHERE t.startedAt IS NULL ORDER BY id DESC ")
	List<Task> findQueued();

    @Query("SELECT t FROM tasks t WHERE t.startedAt IS NOT NULL AND (t.crashedAt IS NULL AND t.finishedAt IS NULL) ORDER BY id DESC ")
    List<Task> findRunning();

    @Query("SELECT t FROM tasks t WHERE t.crashedAt IS NOT NULL OR t.finishedAt IS NOT NULL ORDER BY id DESC")
    List<Task> findEnded();
}