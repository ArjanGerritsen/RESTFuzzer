package nl.ou.se.rest.fuzzer.components.reporter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private static Logger logger = LoggerFactory.getLogger(Reporter.class);

    private static ReportTable reportTable = new ReportTable();

    public static void main(String[] args) {

        // TODO inputs
        String directoryName = "C://temp"; // directory were to find XDEBUG output
        Integer interval = 1; // interval, number of requests, point reductions in graph

        List<Path> files = Stream.of(new File(directoryName).listFiles()).filter(file -> !file.isDirectory())
                .map(file -> file.toPath()).collect(Collectors.toList());

        Report current = null;
        Report previous = null;

        Integer requestCount = 0;

        reportTable.setOrigin("requests");

        for (Path file : files) {
            requestCount++;

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss_SSS");
                LocalDateTime localDateTime = LocalDateTime.parse(file.getFileName().toString(), formatter);

                current = processFile(file, localDateTime);

                if (current != null && previous != null) {
                    current.merge(previous);
                }

                previous = current;

//                for (Entry<String, PhpFile> entry : current.getPhpFiles().entrySet()) {
//                    Object rowKey = requestCount;
//                    Object columnKey = entry.getValue().getShortName();
//                    Object value = entry.getValue().getPercentageExecuted();
//
//                    reportTable.addValue(columnKey, rowKey, value);
//                }

                if (requestCount == 1 || (requestCount % interval == 0)) {
                    Object rowKey = requestCount;
                    Object endpoints = current.codeCoveragePercentageFiltered("C:\\xampp\\apps\\wordpress\\htdocs\\wp-includes\\rest-api\\");
                    Object total = current.codeCoveragePercentage();
                    reportTable.addValue("endpoints", rowKey, endpoints);
                    reportTable.addValue("total", rowKey, total);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        reportTable.printTable();
    }

    private static Report processFile(Path path, LocalDateTime localDateTime) throws IOException {
        logger.debug(String.format("Processing file %s", path.getFileName().toFile()));

        String fileContent = Files.readString(path, StandardCharsets.UTF_8);
        Map<String, Object> objects = JsonUtil.stringToMap(fileContent);

        Report report = new Report();
        report.setTimestamp(localDateTime);
        Map<String, PhpFile> phpFiles = new HashMap<>();
        objects.entrySet().forEach(entry -> {
            phpFiles.put(entry.getKey(), processFileEntry(entry));
        });
        report.setPhpFiles(phpFiles);

        phpFiles.entrySet().forEach(entry -> logger.debug(String.format("File %s", entry.getKey())));

        return report;
    }

    private static PhpFile processFileEntry(Entry<String, Object> entry) {
        PhpFile file = new PhpFile();
        file.setName(entry.getKey());
        JSONObject jsonObject = (JSONObject) entry.getValue();
        jsonObject.keySet().forEach(key -> {
            file.setLinesWithCode(processLineEntry((JSONObject) entry.getValue()));
        });

        return file;
    }

    private static Map<Integer, LineWithCode> processLineEntry(JSONObject jsonObject) {
        Map<Integer, LineWithCode> linesWithCode = new HashMap<>();
        jsonObject.keySet().forEach(key -> {
            LineWithCode lineWithCode = new LineWithCode();
            lineWithCode.setNumber(Integer.parseInt(key));
            lineWithCode.setCount(1 == jsonObject.getInt(key) ? 1 : 0); // 1 => line is executed

            linesWithCode.put(Integer.parseInt(key), lineWithCode);
        });

        return linesWithCode;
    }
}