package nl.ou.se.rest.fuzzer.components.reporter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import nl.ou.se.rest.fuzzer.components.data.report.dao.ReportService;
import nl.ou.se.rest.fuzzer.components.data.report.domain.Report;
import nl.ou.se.rest.fuzzer.components.extractor.ExtractorTask;
import nl.ou.se.rest.fuzzer.components.task.TaskExecution;
import nl.ou.se.rest.fuzzer.components.task.TaskExecutionBase;

public class ReporterTask extends TaskExecutionBase implements TaskExecution {

    // variable(s)
    public static final String KEY_REPORT_ID = "report_id";

    @Autowired
    private ReportService reportService;

    // method(s)
    public void execute() {
        this.logStart(ReporterTask.class.getTypeName());

        Object objReportId = this.getValueForKey(ReporterTask.class, KEY_REPORT_ID);
        if (objReportId == null) {
            return;
        }

        Long reportId = Long.valueOf((Integer) objReportId);
        Optional<Report> oReport = reportService.findById(reportId);

        if (!this.isOptionalPresent(ExtractorTask.class, oReport, reportId)) {
            return;
        }

        Report report = oReport.get();

        switch (report.getType()) {
        case CODE_COVERAGE:
            break;
        case RESPONSES:
            break;
        default:
            break;
        }

        // TODO ...
        
//        getTask().setProgress(new BigDecimal(100));
//        taskService.save(getTask());

        this.logStop(ReporterTask.class.getTypeName());
    }
}