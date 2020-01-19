package nl.ou.se.rest.fuzzer.job;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.data.job.dao.JobService;
import nl.ou.se.rest.fuzzer.data.job.domain.Job;

@Service
public class JobsSchedular {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private JobsExecutor jobsExecutor;

	@Autowired 
	private JobService jobService;

	@Scheduled(cron = "*/10 * * * * *")
	public void runJobs() {
		Stream<Job> jobsToRun = jobService.jobNotStarted();
		logger.info(String.format("Starting %s job(s)", jobsToRun.count()));

		// jobsExecutor.execute(new ExtractorJob());
	}	
}