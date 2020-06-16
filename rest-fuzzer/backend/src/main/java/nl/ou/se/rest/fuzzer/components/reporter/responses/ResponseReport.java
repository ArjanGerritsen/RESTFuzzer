package nl.ou.se.rest.fuzzer.components.reporter.responses;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzResponseService;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;

@Service
public class ResponseReport {

	// variable(s)
	private FuzProject project;
	private Integer intervalInSeconds;
	private Map<Long, List<Object[]>> data = new HashMap<>();

	@Autowired
	private FuzResponseService responseService;

	// constructor(s)
	public ResponseReport() {
	}

	// method(s)
	public void init(FuzProject project, Integer intervalInSeconds) {
		this.project = project;
		this.intervalInSeconds = intervalInSeconds;
	}

	public void create() {
		List<Integer> statusCodes = responseService.findUniqueStatusCodesForProject(this.project.getId());

		LocalDateTime start = responseService.findMinCreatedByProjectId(this.project.getId());

		LocalDateTime from = null;
		LocalDateTime to = null;

		do {
			from = (to == null ? start : from);
			to = from.plusSeconds(this.intervalInSeconds);

			List<Object[]> responseCodesAndCounts = responseService
					.findStatusCodesAndCountsByProjectIdAndDateAndInterval(this.project.getId(), from, to);

			data.put(ChronoUnit.SECONDS.between(start, from), responseCodesAndCounts);

			if (responseCodesAndCounts.isEmpty()) {
				break;
			}
		} while (true);

	}

	public static void main(String[] args) {
		FuzProject project = new FuzProject();
		project.setId((long) 1);

		ResponseReport responseReport = new ResponseReport();
		responseReport.init(project, 50);
		responseReport.create();
	}

}