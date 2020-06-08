package nl.ou.se.rest.fuzzer.components.reporter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PhpFile {

    // variable(s)
    private String name;
    private Map<Integer, LineWithCode> linesWithCode = new HashMap<>();

    // method(s)
    public void merge(PhpFile phpFile) {
        if (phpFile == null) {
            return;
        }

        if (!this.getName().equals(phpFile.getName())) {
            throw new IllegalArgumentException("Names should match");
        }

        this.linesWithCode.entrySet().forEach(entry -> {
            entry.getValue().merge(phpFile.getLinesWithCode().get(entry.getKey()));
        });
    }

    public double codeCoveragePercentage() {
        Double locExecuted = (double) this.locCount(true);
        Double locNotExecuted = (double) this.locCount(false);
        return (locExecuted / (locExecuted + locNotExecuted)) * 100;
    }

    public int locCount(boolean isExecuted) {
        int count = 0;
        for (LineWithCode line : this.linesWithCode.values()) {
            if (isExecuted == line.isExecuted()) {
                count += 1;
            }
        }
        return count;
    }

    public String path() {
        Path path = Paths.get(this.name);
        return path.getParent().toString();
    }

    public String shortName() {
        Path path = Paths.get(this.name);
        return path.getFileName().toString();
    }

    // getter(s) and setter(s)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, LineWithCode> getLinesWithCode() {
        return linesWithCode;
    }

    public void setLinesWithCode(Map<Integer, LineWithCode> linesWithCode) {
        this.linesWithCode = linesWithCode;
    }
}