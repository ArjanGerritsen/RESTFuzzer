package nl.ou.se.rest.fuzzer.components.reporter.coverage;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CoverageReport {

    // variable(s)
    private Map<String, PhpFile> phpFiles = new HashMap<>();

    // method(s)
    public void merge(CoverageReport report) {
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
}