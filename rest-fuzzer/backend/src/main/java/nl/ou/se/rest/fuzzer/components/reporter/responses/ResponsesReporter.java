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
    private static final String SEPERATOR_COMMA = ",";

    private static final String HEADER_TIME_PASSED = "time";
    private static final String HEADER_TOTAL_REQUESTS = "responses";

    // variable(s) for velocity template
    private static final String VM_DATA_ROWS = "dataRows";
    private static final String VM_PLOTS = "plots";
    private static final String VM_X_TICKS_LABELS = "xTicksLabels";
    private static final String VM_X_TICKS = "xTicks";
    private static final String VM_X_MAX = "xMax";
    private static final String VM_Y_TICKS = "yTicks";

    private Report report;
    private Task task;
    private List<Integer> statusCodes;

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
        this.statusCodes = responseService.findUniqueStatusCodesForProject(report.getProject().getId());

        data.clear(); // reset
    }

    public void generate() {
        task.setProgress(new BigDecimal(10));
        taskService.save(task);

        gatherData();

        task.setProgress(new BigDecimal(60));
        taskService.save(this.task);

        setOutput();

        task.setProgress(new BigDecimal(100));
        taskService.save(this.task);
    }

    private void gatherData() {
        LocalDateTime start = responseService.findMinCreatedByProjectId(report.getProject().getId());
        Integer pointsInterval = this.metaDataUtil.getIntegerValue(Meta.POINTS_INTERVAL);

        LocalDateTime from = null;
        LocalDateTime to = null;

        do {
            from = ((to == null) ? start : to);
            to = from.plusSeconds(pointsInterval);

            List<Object[]> statusCodesAndCounts = responseService
                    .findStatusCodesAndCountsByProjectIdAndDateAndInterval(report.getProject().getId(), from, to);

            if (statusCodesAndCounts.isEmpty()) {
                break;
            }

            data.put(ChronoUnit.SECONDS.between(start, to), statusCodesAndCounts);

        } while (true);
    }

    private void setOutput() {
        Properties properties = new Properties();
        properties.setProperty("resource.loaders", "class");
        properties.setProperty("resource.loader.class.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        VelocityEngine ve = new VelocityEngine();
        ve.init(properties);

        Template t = ve.getTemplate("velocity/report-responses.vm");

        VelocityContext vc = new VelocityContext();

        List<List<Object>> dataLines = getDataLines();
        List<String> dataLineStrings = dataLines.stream().map(line -> ObjectListtoString(line, SEPERATOR_COMMA))
                .collect(Collectors.toList());

        vc.put(VM_DATA_ROWS, dataLineStrings);
        vc.put(VM_PLOTS, getPlots());

        List<Integer> xTicks = getXticks(dataLines);

        vc.put(VM_X_MAX, xTicks.remove(xTicks.size() - 1));
        vc.put(VM_X_TICKS, ObjectListtoString(xTicks, SEPERATOR_COMMA));
        vc.put(VM_X_TICKS_LABELS, ObjectListtoString(getXticksLabels(dataLines, xTicks), SEPERATOR_COMMA));
        vc.put(VM_Y_TICKS, ObjectListtoString(getYticks(dataLines), SEPERATOR_COMMA));

        StringWriter sw = new StringWriter();
        t.merge(vc, sw);

        this.report.setOutput(sw.toString());
        this.report.setCompletedAt(LocalDateTime.now());
        reportService.save(this.report);
    }

    private List<List<Object>> getDataLines() {
        List<List<Object>> lines = new ArrayList<>();

        // header line
        List<Object> columnHeaders = new ArrayList<>();
        columnHeaders.add(HEADER_TIME_PASSED);
        columnHeaders.add(HEADER_TOTAL_REQUESTS);
        this.statusCodes.forEach(c -> columnHeaders.add(c.toString()));
        lines.add(columnHeaders);

        // line with zeros for each column
        List<Object> zeros = new ArrayList<>();
        IntStream.rangeClosed(1, columnHeaders.size()).forEach(i -> zeros.add(0));
        lines.add(zeros);

        // values
        Integer totalRequests = 0;
        SortedMap<Integer, Integer> statusCodesTotals = new TreeMap<>();

        for (Entry<Long, List<Object[]>> entry : this.data.entrySet()) {
            List<Object> dataLine = new ArrayList<>();

            dataLine.add(entry.getKey().intValue()); // time passed

            totalRequests += entry.getValue().stream().mapToInt(statusAndCount -> {
                return Long.valueOf((long) statusAndCount[1]).intValue();
            }).sum();

            // cumulative response count total
            dataLine.add(totalRequests);

            for (Integer statusCode : this.statusCodes) {
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

            lines.add(dataLine);
        }

        return lines;
    }

    private List<String> getPlots() {
        List<String> plots = new ArrayList<>();
        this.statusCodes.forEach(c -> plots.add(c.toString()));
        return plots;
    }

    private List<Integer> getYticks(List<List<Object>> dataLines) {
        Integer interval = this.metaDataUtil.getIntegerValue(Meta.Y_TICK_INTERVAL);

        List<Object> lastLine = dataLines.get(dataLines.size() - 1);
        Integer max = lastLine.subList(2, lastLine.size() - 1).stream().mapToInt(v -> (Integer) v).max().getAsInt();

        return getTicks(interval, max);
    }

    private List<Integer> getXticks(List<List<Object>> dataLines) {
        Integer interval = this.metaDataUtil.getIntegerValue(Meta.X_TICK_INTERVAL);

        List<Object> lastLine = dataLines.get(dataLines.size() - 1);
        Integer max = (Integer) lastLine.get(1);

        return getTicks(interval, max);
    }

    private List<Integer> getXticksLabels(List<List<Object>> dataLines, List<Integer> xTicks) {
        List<Integer> xTicksLabels = new ArrayList<>();

        for (Integer xTick : xTicks) {
            for (List<Object> dataLine : dataLines.subList(1, dataLines.size())) {
                Integer time = (Integer) dataLine.get(0);
                if (time.equals(xTick)) {
                    Integer numRequests = (Integer) dataLine.get(1);
                    xTicksLabels.add(numRequests);
                    break;
                }
            }
        }

        return xTicksLabels;
    }

    private List<Integer> getTicks(Integer interval, Integer max) {
        List<Integer> ticks = new ArrayList<>();

        Integer curValue = 0;
        ticks.add(curValue);
        while (curValue <= max) {
            curValue += interval;
            ticks.add(curValue);
        }

        return ticks;
    }

    private String ObjectListtoString(List<?> values, String seperator) {
        return values.stream().map(tick -> tick.toString()).collect(Collectors.joining(seperator));
    }

    public Boolean isMetaDataValid(Map<String, Object> metaDataTuples) {
        this.metaDataUtil = new MetaDataUtil(metaDataTuples);
        return metaDataUtil.isValid(Meta.POINTS_INTERVAL, Meta.X_TICK_INTERVAL, Meta.Y_TICK_INTERVAL);
    }
}