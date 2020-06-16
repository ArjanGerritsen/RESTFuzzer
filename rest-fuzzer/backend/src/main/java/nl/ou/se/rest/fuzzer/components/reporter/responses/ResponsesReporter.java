package nl.ou.se.rest.fuzzer.components.reporter.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzResponseService;
import nl.ou.se.rest.fuzzer.components.data.report.dao.ReportService;
import nl.ou.se.rest.fuzzer.components.data.report.domain.Report;
import nl.ou.se.rest.fuzzer.components.data.task.dao.TaskService;
import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;
import nl.ou.se.rest.fuzzer.components.fuzzer.metadata.MetaDataUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.metadata.MetaDataUtil.Meta;
import nl.ou.se.rest.fuzzer.components.reporter.Reporter;

@Service
public class ResponsesReporter implements Reporter {

    // variable(s)
    private static final String SEPERATOR_COLUMN = ",";
    private static final String SEPERATOR_ROW = "\n";

    private static final String HEADER_TIME_PASSED = "time";
    private static final String HEADER_TOTAL_REQUESTS = "requests";

    private MetaDataUtil metaDataUtil = null;
    private SortedMap<Long, List<Object[]>> data = new TreeMap<>();

    @Autowired
    private FuzResponseService responseService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private TaskService taskService;

    // constructor(s)
    public ResponsesReporter() {
    }

    // method(s)
    public void generate(Report report, Task task) {
        data.clear(); // reset

        Integer interval = this.metaDataUtil.getIntegerValue(Meta.INTERVAL);

        LocalDateTime start = responseService.findMinCreatedByProjectId(report.getProject().getId());

        task.setProgress(new BigDecimal(10));
        taskService.save(task);

        gatherData(report, task, interval, start);

        List<Integer> statusCodes = responseService.findUniqueStatusCodesForProject(report.getProject().getId());

        convertAndSettData(report, statusCodes);

        reportService.save(report);

        task.setProgress(new BigDecimal(100));
        taskService.save(task);
    }

    private void gatherData(Report report, Task task, Integer interval, LocalDateTime start) {
        LocalDateTime from = null;
        LocalDateTime to = null;

        do {
            from = ((to == null) ? start : to);
            to = from.plusSeconds(interval);

            List<Object[]> statusCodesAndCounts = responseService
                    .findStatusCodesAndCountsByProjectIdAndDateAndInterval(report.getProject().getId(), from, to);

            if (statusCodesAndCounts.isEmpty()) {
                break;
            }

            data.put(ChronoUnit.SECONDS.between(start, to), statusCodesAndCounts);

            task.setProgress(task.getProgress().add(new BigDecimal(0.1)));
            taskService.save(task);
        } while (true);

        task.setProgress(new BigDecimal(90));
        taskService.save(task);
    }

    private void convertAndSettData(Report report, List<Integer> statusCodes) {
        StringBuilder sb = new StringBuilder();

        // header
        sb.append(HEADER_TIME_PASSED);
        sb.append(SEPERATOR_COLUMN);
        sb.append(HEADER_TOTAL_REQUESTS);

        for (Integer statusCode : statusCodes) {
            sb.append(SEPERATOR_COLUMN);
            sb.append(statusCode);
        }

        sb.append(SEPERATOR_ROW);

        // data
        for (Entry<Long, List<Object[]>> entry : this.data.entrySet()) {
            sb.append(entry.getKey());
            sb.append(SEPERATOR_COLUMN);
            sb.append(entry.getValue().stream().mapToLong(statusAndCount -> {
                return (long) statusAndCount[1];
            }).sum());

            for (Integer statusCode : statusCodes) {
                sb.append(SEPERATOR_COLUMN);

                Long count = entry.getValue().stream().filter(statusAndCount -> {
                    return statusAndCount[0].equals(statusCode);
                }).mapToLong(statusAndCount -> {
                    return (long) statusAndCount[1];
                }).sum();

                sb.append(count);
            }

            sb.append(SEPERATOR_ROW);
        }

        report.setCompletedAt(LocalDateTime.now());
        report.setOutput(sb.toString());
    }

    public Boolean isMetaDataValid(Map<String, Object> metaDataTuples) {
        this.metaDataUtil = new MetaDataUtil(metaDataTuples);
        return metaDataUtil.isValid(Meta.INTERVAL);
    }
}