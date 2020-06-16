package nl.ou.se.rest.fuzzer.components.reporter.coverage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.ou.se.rest.fuzzer.components.shared.JsonUtil;

public class Reporter {

    // variable(s)
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static ReportTable reportTable = new ReportTable();

    public static void main(String[] args) {

        // TODO inputs
        String directoryName = "C://temp"; // directory were to find XDEBUG output
        Integer interval = 10; // interval, number of requests, point reductions in graph

        List<Path> files = Stream.of(new File(directoryName).listFiles()).filter(file -> !file.isDirectory())
                .map(file -> file.toPath()).collect(Collectors.toList());

        CoverageReport current = null;
        CoverageReport previous = null;

        Integer requestCount = 0;

        reportTable.setOrigin("requests");

        LocalDateTime startTime = null;

        for (Path file : files) {
            requestCount++;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss_SSS");
            LocalDateTime localDateTime = LocalDateTime.parse(file.getFileName().toString(), formatter);

            if (startTime == null) {
                startTime = localDateTime;
            }

            try {
                current = processFile(file);

                if (current != null && previous != null) {
                    current.merge(previous);
                }

                previous = current;

                if (requestCount == 1 || (requestCount % interval == 0)) {
                    Object rowKey = requestCount;
                    Object secondsPassed = ChronoUnit.SECONDS.between(startTime, localDateTime);
                    Object endpoints = current.codeCoveragePercentageFiltered(
                            "C:\\xampp\\apps\\wordpress\\htdocs\\wp-includes\\rest-api\\");
                    Object total = current.codeCoveragePercentage();
                    reportTable.addValue("secondspassed", rowKey, secondsPassed);
                    reportTable.addValue("endpoints", rowKey, endpoints);
                    reportTable.addValue("total", rowKey, total);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        reportTable.printTable();
    }

    private static CoverageReport processFile(Path path) throws IOException {
        LocalDateTime from = LocalDateTime.now();

        String fileContent = Files.readString(path, StandardCharsets.UTF_8);
        Map<String, Object> objects = JsonUtil.stringToMap(fileContent);

        CoverageReport coverageReport = new CoverageReport();
        
	Map<String, PhpFile> phpFiles = new HashMap<>();
        objects.entrySet().forEach(entry -> {
            phpFiles.put(entry.getKey(), processFileEntry(entry));
        });
        coverageReport.setPhpFiles(phpFiles);

        long millisPassed = ChronoUnit.MILLIS.between(from, LocalDateTime.now());
        // logger.debug(String.format("processed file %s in %s ms", path.getFileName().toFile(), millisPassed));

        return coverageReport;
    }

    private static PhpFile processFileEntry(Entry<String, Object> entry) {
        PhpFile file = new PhpFile();
        file.setName(entry.getKey());
        JSONObject jsonObject = (JSONObject) entry.getValue();
        jsonObject.keySet().forEach(key -> {
            processLineEntry(file, (JSONObject) entry.getValue());
        });

        return file;
    }

    private static void processLineEntry(PhpFile phpFile, JSONObject jsonObject) {
        List<Integer> linesExecuted = new ArrayList<>();
        List<Integer> linesNotExecuted = new ArrayList<>();

        jsonObject.keySet().forEach(key -> {
            // 1 => line is executed
            if (1 == jsonObject.getInt(key)) {
                linesExecuted.add(Integer.parseInt(key));
            } else {
                linesNotExecuted.add(Integer.parseInt(key));
            }
        });

        phpFile.setLinesExecuted(linesExecuted);
        phpFile.setLinesNotExecuted(linesNotExecuted);
    }
}
