package nl.ou.se.rest.fuzzer.components.reporter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    public int fileCount() {
        return this.phpFiles.entrySet().size();
    }

    public int locCount(boolean isExecuted) {
        int count = 0;
        for (PhpFile phpFile : this.phpFiles.values()) {
            count += phpFile.getLocCount(isExecuted);
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