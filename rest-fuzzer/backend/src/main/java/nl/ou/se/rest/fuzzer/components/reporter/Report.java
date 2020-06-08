package nl.ou.se.rest.fuzzer.components.reporter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Report {

    // variable(s)
    private LocalDateTime timestamp;
    private Map<String, PhpFile> phpFiles = new HashMap<>();

    // method(s)
    public void merge(Report report) {
        if (report == null) {
            return;
        }

        if (this.getTimestamp() == report.getTimestamp()) {
            throw new IllegalArgumentException("Timestamps shouldn't match");
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
                locExecuted += phpFile.locCount(true);
                locNotExecuted += phpFile.locCount(false);
            }
        }

        if (locExecuted + locNotExecuted == 0) {
            return 0;
        }

        return (locExecuted / (locExecuted + locNotExecuted)) * 100;
    }

    public double codeCoveragePercentage() {
        Double locExecuted = (double) this.locCount(true);
        Double locNotExecuted = (double) this.locCount(false);

        if (locExecuted + locNotExecuted == 0) {
            return 0;
        }

        return (locExecuted / (locExecuted + locNotExecuted)) * 100;
    }

    public int fileCount() {
        return this.phpFiles.entrySet().size();
    }

    public int locCount(boolean isExecuted) {
        int count = 0;
        for (PhpFile phpFile : this.phpFiles.values()) {
            count += phpFile.locCount(isExecuted);
        }
        return count;
    }

    // getter(s) and setter(s)
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, PhpFile> getPhpFiles() {
        return phpFiles;
    }

    public void setPhpFiles(Map<String, PhpFile> phpFiles) {
        this.phpFiles = phpFiles;
    }
}