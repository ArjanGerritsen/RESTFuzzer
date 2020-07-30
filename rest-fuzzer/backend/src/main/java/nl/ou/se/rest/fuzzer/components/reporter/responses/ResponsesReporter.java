package nl.ou.se.rest.fuzzer.components.reporter.responses;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzResponseService;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzResponse;
import nl.ou.se.rest.fuzzer.components.data.report.dao.ReportService;
import nl.ou.se.rest.fuzzer.components.data.report.domain.Report;
import nl.ou.se.rest.fuzzer.components.data.task.dao.TaskService;
import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;
import nl.ou.se.rest.fuzzer.components.fuzzer.metadata.MetaDataUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.metadata.MetaDataUtil.Meta;
import nl.ou.se.rest.fuzzer.components.reporter.Reporter;
import nl.ou.se.rest.fuzzer.components.reporter.ReporterBase;

@Service
public class ResponsesReporter extends ReporterBase implements Reporter {

    // variable(s)
    private static final String SEPERATOR_COMMA = ",";

    private static final String HEADER_TIME_PASSED = "time";
    private static final String HEADER_TOTAL_RESPONSES = "responses";

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
    private DataTable dataTable = new DataTable();

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

        dataTable.reset();
    }

    public void generate() {
        this.task.setProgress(new BigDecimal(10));
        this.taskService.save(task);

        gatherData();

        this.task.setProgress(new BigDecimal(60));
        this.taskService.save(this.task);

        this.report.setOutput(parseTemplate());
        this.report.setCompletedAt(LocalDateTime.now());
        this.reportService.save(this.report);

        this.task.setProgress(new BigDecimal(100));
        this.taskService.save(this.task);
    }

    private void gatherData() {
        Integer page = 1;
        Integer pointsInterval = this.metaDataUtil.getIntegerValue(Meta.POINTS_INTERVAL);

        /**
         * TODO, Eigenlijk createdAt van request die bij deze response hoort.
         */
        FuzResponse firstResponse = responseService.findByProjectId(report.getProject().getId(), PageRequest.of(0, 1)).get(0);

        do {
            List<FuzResponse> responses = responseService.findByProjectId(report.getProject().getId(), PageRequest.of((page * pointsInterval), 1));
            if (responses.isEmpty()) {
                break;
            }

            FuzResponse response = responses.get(0);

            List<Object[]> statusCodesAndCounts = responseService.findStatusCodesAndCountsByProjectIdAndMaxId(report.getProject().getId(), response.getId());

            if (statusCodesAndCounts.isEmpty()) {
                break;
            }

            response = responseService.findByProjectId(report.getProject().getId(), PageRequest.of(page * pointsInterval, 1)).get(0);
            Long secondsPassed = ChronoUnit.SECONDS.between(firstResponse.getCreatedAt(), response.getCreatedAt());

            dataTable.add(page * pointsInterval, secondsPassed, statusCodesAndCounts);

            page++;

        } while (true);
    }

    private String parseTemplate() {
        VelocityEngine ve = this.getVelocityEngine(); 

        Template t = ve.getTemplate("velocity/report-responses.vm");

        VelocityContext vc = new VelocityContext();

        List<List<Object>> dataLines = getHeaderLines();
        dataLines.addAll(dataTable.getDataLines(this.statusCodes));

        List<String> dataLineStrings = dataLines.stream().map(line -> ObjectListtoString(line, SEPERATOR_COMMA))
                .collect(Collectors.toList());

        vc.put(VM_DATA_ROWS, dataLineStrings);
        vc.put(VM_PLOTS, getPlots());

        Integer xTicksInterval = this.metaDataUtil.getIntegerValue(Meta.X_TICK_INTERVAL);
        List<Integer> xTicks = getXticks(dataLines, xTicksInterval);

        vc.put(VM_X_MAX, xTicks.remove(xTicks.size() - 1));
        vc.put(VM_X_TICKS, ObjectListtoString(xTicks, SEPERATOR_COMMA));
        vc.put(VM_X_TICKS_LABELS, ObjectListtoString(getXticksLabels(dataLines.subList(1, dataLines.size()), xTicks), SEPERATOR_COMMA));

        Integer yTicksInterval = this.metaDataUtil.getIntegerValue(Meta.Y_TICK_INTERVAL);
        vc.put(VM_Y_TICKS, ObjectListtoString(getYticks(dataLines, yTicksInterval), SEPERATOR_COMMA));

        StringWriter sw = new StringWriter();
        t.merge(vc, sw);

        return sw.toString();
    }
    
    private List<List<Object>> getHeaderLines() {
        List<List<Object>> headerLines = new ArrayList<List<Object>>();
        
        // header line
        List<Object> columnHeaders = new ArrayList<>();
        columnHeaders.add(HEADER_TOTAL_RESPONSES);
        columnHeaders.add(HEADER_TIME_PASSED);
        statusCodes.forEach(c -> columnHeaders.add(c.toString()));
        headerLines.add(columnHeaders);

        // line with zeros for each column
        List<Object> zeros = new ArrayList<>();
        IntStream.rangeClosed(1, columnHeaders.size()).forEach(i -> zeros.add(0));
        headerLines.add(zeros);
        
        return headerLines;
    }

    private List<String> getPlots() {
        List<String> plots = new ArrayList<>();
        this.statusCodes.forEach(c -> plots.add(c.toString()));
        return plots;
    }

    public Boolean isMetaDataValid(Map<String, Object> metaDataTuples) {
        this.metaDataUtil = new MetaDataUtil(metaDataTuples);
        return metaDataUtil.isValid(Meta.POINTS_INTERVAL, Meta.X_TICK_INTERVAL, Meta.Y_TICK_INTERVAL);
    }
}