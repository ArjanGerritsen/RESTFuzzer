package nl.ou.se.rest.fuzzer.components.reporter.responses;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
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

    private static final String SEPERATOR_TAB = "\t";

    private static final String HEADER_TIME_PASSED = "time";
    private static final String HEADER_TOTAL_REQUESTS = "reqs";

    // variable(s) for velocity template 
    private static final String VM_LINES = "lines";

    private Report report;
    private Task task;
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
    public void init(Report report, Task task) {
        this.report = report;
        this.task = task;

        data.clear(); // reset
    }

    public void generate() {
        task.setProgress(new BigDecimal(10));
        taskService.save(task);

        gatherData();

        convertAndSettData();

        getOutput();

        reportService.save(report);

        task.setProgress(new BigDecimal(100));
        taskService.save(task);
    }

    private void getOutput() {
        Properties properties = new Properties();
        properties.setProperty("resource.loaders", "class");
        properties.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        VelocityEngine ve = new VelocityEngine();
        ve.init(properties);

        Template t = ve.getTemplate("velocity/report-responses.vm");

        VelocityContext vc = new VelocityContext();
        vc.put(VM_LINES, getDataLines());

        StringWriter sw = new StringWriter();
        t.merge(vc, sw);

        System.out.println(sw);
    }

    private List<String> getDataLines() {
        List<String> lines = new ArrayList<>();
        List<Integer> statusCodes = responseService.findUniqueStatusCodesForProject(report.getProject().getId());

        // header line
        List<String> columnHeaders = new ArrayList<String>();
        columnHeaders.add(HEADER_TIME_PASSED);
        columnHeaders.add(HEADER_TOTAL_REQUESTS);
        statusCodes.forEach(c -> columnHeaders.add(c.toString()));
        lines.add(columnHeaders.stream().collect(Collectors.joining(SEPERATOR_TAB)));

        // line with zeros for each column
        List<Integer> zeros = new ArrayList<>();
        IntStream.rangeClosed(1, columnHeaders.size()).forEach(i -> zeros.add(0));
        lines.add(zeros.stream().map(z -> z.toString()).collect(Collectors.joining(SEPERATOR_TAB)));

        // values
        Integer totalRequests = 0;
        List<Integer> dataLine = new ArrayList<>();
        SortedMap<Integer, Integer> statusCodesTotals = new TreeMap<>();

        for (Entry<Long, List<Object[]>> entry : this.data.entrySet()) {
            dataLine.add(entry.getKey().intValue()); // time passed

            totalRequests += entry.getValue().stream().mapToInt(statusAndCount -> {
                return Long.valueOf((long) statusAndCount[1]).intValue();
            }).sum();

            // cumulative response count total
            dataLine.add(totalRequests);

            for (Integer statusCode : statusCodes) {
                Integer statusCodeCount = entry.getValue().stream().filter(statusAndCount -> {
                    return statusAndCount[0].equals(statusCode);
                }).mapToInt(statusAndCount -> {
                    return Long.valueOf((long) statusAndCount[1]).intValue();
                }).sum();

                if (statusCodesTotals.containsKey(statusCode)) {
                    statusCodeCount += statusCodesTotals.get(statusCode);
                }
                statusCodesTotals.put(statusCode, statusCodeCount);

                // cumulative response count for current response type
                dataLine.add(statusCodeCount);
            }

            lines.add(dataLine.stream().map(z -> z.toString()).collect(Collectors.joining(SEPERATOR_TAB)));
            dataLine.clear();
        }

        return lines;
    }

    private void gatherData() {
        LocalDateTime start = responseService.findMinCreatedByProjectId(report.getProject().getId());
        Integer interval = this.metaDataUtil.getIntegerValue(Meta.INTERVAL);

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

        } while (true);

        task.setProgress(new BigDecimal(50));
        taskService.save(task);
    }

    private void convertAndSettData() {
        List<Integer> statusCodes = responseService.findUniqueStatusCodesForProject(report.getProject().getId());

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
        Integer totalRequests = 0;
        SortedMap<Integer, Integer> statusCodesTotals = new TreeMap<>();

        for (Entry<Long, List<Object[]>> entry : this.data.entrySet()) {
            sb.append(entry.getKey());
            sb.append(SEPERATOR_COLUMN);

            totalRequests += entry.getValue().stream().mapToInt(statusAndCount -> {
                return Long.valueOf((long) statusAndCount[1]).intValue();
            }).sum();

            sb.append(totalRequests);

            for (Integer statusCode : statusCodes) {
                sb.append(SEPERATOR_COLUMN);

                Integer statusCodeCount = entry.getValue().stream().filter(statusAndCount -> {
                    return statusAndCount[0].equals(statusCode);
                }).mapToInt(statusAndCount -> {
                    return Long.valueOf((long) statusAndCount[1]).intValue();
                }).sum();

                if (statusCodesTotals.containsKey(statusCode)) {
                    statusCodeCount += statusCodesTotals.get(statusCode);
                }
                statusCodesTotals.put(statusCode, statusCodeCount);

                sb.append(statusCodeCount);
            }

            sb.append(SEPERATOR_ROW);
        }

        report.setCompletedAt(LocalDateTime.now());
        report.setOutput(sb.toString());

        task.setProgress(new BigDecimal(90));
        taskService.save(task);
    }

    public Boolean isMetaDataValid(Map<String, Object> metaDataTuples) {
        this.metaDataUtil = new MetaDataUtil(metaDataTuples);
        return metaDataUtil.isValid(Meta.INTERVAL);
    }
}