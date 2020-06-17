package nl.ou.se.rest.fuzzer.components.reporter.coverage;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.report.domain.Report;
import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;
import nl.ou.se.rest.fuzzer.components.reporter.Reporter;

@Service
public class CoverageReporter implements Reporter {

    // variable(s)
    private Report report;
    private Task task;
    private Map<String, PhpFile> phpFiles = new HashMap<>();

    // method(s)
    public void init(Report report, Task task) {
        this.report = report;
        this.task = task;
    }

    public void merge(CoverageReporter report) {
        if (report == null) {
            return;
        }

        this.phpFiles.entrySet().forEach(entry -> {
            entry.getValue().merge(report.getPhpFiles().get(entry.getKey()));
        });
    }

    public Object codeCoveragePercentageFiltered(String filter) {
        Double locExecuted = Double.valueOf(0);
        Double locNotExecuted = Double.valueOf(0);

        for (Entry<String, PhpFile> entry : this.phpFiles.entrySet()) {
            PhpFile phpFile = entry.getValue();
            if (phpFile.path().startsWith(filter)) {
                locExecuted += phpFile.getLinesExecuted().size();
                locNotExecuted += phpFile.getLinesNotExecuted().size();
            }
        }

        if (locExecuted + locNotExecuted == 0) {
            return 0;
        }

        return (locExecuted / (locExecuted + locNotExecuted)) * 100;
    }

    public double codeCoveragePercentage() {
        Double locExecuted = Double.valueOf(0);
        Double locNotExecuted = Double.valueOf(0);
        for (PhpFile phpFile : this.phpFiles.values()) {
            locExecuted += phpFile.getLinesExecuted().size();
            locNotExecuted += phpFile.getLinesNotExecuted().size();
        }

        if (locExecuted + locNotExecuted == 0) {
            return 0;
        }

        return (locExecuted / (locExecuted + locNotExecuted)) * 100;
    }

    public int fileCount() {
        return this.phpFiles.entrySet().size();
    }

    // getter(s) and setter(s)
    public Map<String, PhpFile> getPhpFiles() {
        return phpFiles;
    }

    public void setPhpFiles(Map<String, PhpFile> phpFiles) {
        this.phpFiles = phpFiles;
    }

    @Override
    public void generate() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public Boolean isMetaDataValid(Map<String, Object> metaDataTuples) {
        // TODO Auto-generated method stub
        return null;
    }
}