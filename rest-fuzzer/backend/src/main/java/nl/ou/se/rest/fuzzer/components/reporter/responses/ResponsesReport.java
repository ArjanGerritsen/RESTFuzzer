package nl.ou.se.rest.fuzzer.components.reporter.responses;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzResponseService;
import nl.ou.se.rest.fuzzer.components.data.report.domain.Report;
import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;
import nl.ou.se.rest.fuzzer.components.reporter.Reporter;

@Service
public class ResponsesReport implements Reporter {

    // variable(s)
    private Map<Long, List<Object[]>> data = new HashMap<>();

    @Autowired
    private FuzResponseService responseService;

    // constructor(s)
    public ResponsesReport() {
    }

    // method(s)
    public void start(Report report, Task task) {
        Integer intervalInSeconds = 10; // TODO

        List<Integer> statusCodes = responseService.findUniqueStatusCodesForProject(report.getProject().getId());

        LocalDateTime start = responseService.findMinCreatedByProjectId(report.getProject().getId());

        LocalDateTime from = null;
        LocalDateTime to = null;

        do {
            from = (to == null ? start : from);
            to = from.plusSeconds(intervalInSeconds);

            List<Object[]> responseCodesAndCounts = responseService
                    .findStatusCodesAndCountsByProjectIdAndDateAndInterval(report.getProject().getId(), from, to);

            data.put(ChronoUnit.SECONDS.between(start, from), responseCodesAndCounts);

            if (responseCodesAndCounts.isEmpty()) {
                break;
            }
        } while (true);
    }

    public Boolean isMetaDataValid(Map<String, Object> metaDataTuples) {
        // TODO Auto-generated method stub
        return null;
    }
}