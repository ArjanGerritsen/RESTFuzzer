package nl.ou.se.rest.fuzzer.data.job.dao;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.ou.se.rest.fuzzer.data.job.domain.Job;

public interface JobService extends CrudRepository<Job, Long> {

	@Query("SELECT j FROM jobs j WHERE j.started_at IS NULL")
	Stream<Job> jobNotStarted();
	
}