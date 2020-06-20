package nl.ou.se.rest.fuzzer.components.reporter.coverage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.report.dao.ReportService;
import nl.ou.se.rest.fuzzer.components.data.report.domain.Report;
import nl.ou.se.rest.fuzzer.components.data.task.dao.TaskService;
import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;
import nl.ou.se.rest.fuzzer.components.fuzzer.metadata.MetaDataUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.metadata.MetaDataUtil.Meta;
import nl.ou.se.rest.fuzzer.components.reporter.Reporter;
import nl.ou.se.rest.fuzzer.components.reporter.coverage.calculation.CoverageFile;
import nl.ou.se.rest.fuzzer.components.reporter.coverage.calculation.PhpFile;
import nl.ou.se.rest.fuzzer.components.shared.JsonUtil;

@Service
public class CoverageReporter implements Reporter {

    // variable(s)
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static String PATH_XDEBUG_FILES = "C://temp";
    private static String PATH_ENDPOINTS = "C:\\xampp\\apps\\wordpress\\htdocs\\wp-includes\\rest-api\\";

    private Report report;
    private Task task;

    private MetaDataUtil metaDataUtil = null;
    private List<List<Object>> dataLines = new ArrayList<>();

    @Autowired
    private ReportService reportService;

    @Autowired
    private TaskService taskService;

    // method(s)
    @Override
    public void init(Report report, Task task) {
        this.report = report;
        this.task = task;

        dataLines.clear(); // reset
    }

    public void generate() {
        gatherData();

        this.report.setOutput(parseTemplate());
        this.report.setCompletedAt(LocalDateTime.now());
        this.reportService.save(this.report);

        task.setProgress(new BigDecimal(100));
        taskService.save(this.task);
    }

    public Boolean isMetaDataValid(Map<String, Object> metaDataTuples) {
        this.metaDataUtil = new MetaDataUtil(metaDataTuples);
        return metaDataUtil.isValid(Meta.POINTS_INTERVAL, Meta.X_TICK_INTERVAL, Meta.Y_TICK_INTERVAL);
    }

    private void gatherData() {
        Integer pointsInterval = this.metaDataUtil.getIntegerValue(Meta.POINTS_INTERVAL);

        List<Path> filesOnDisk = Stream.of(new File(PATH_XDEBUG_FILES).listFiles()).filter(file -> !file.isDirectory())
                .map(file -> file.toPath()).collect(Collectors.toList());

        CoverageFile current = null;
        CoverageFile previous = null;

        Integer responsesCount = 0;
        LocalDateTime startTime = null;

        for (Path fileOnDisk : filesOnDisk) {
            responsesCount++;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss_SSS");
            LocalDateTime localDateTime = LocalDateTime.parse(fileOnDisk.getFileName().toString(), formatter);

            if (startTime == null) {
                startTime = localDateTime;
            }

            try {
                current = processFile(fileOnDisk);

                if (current != null && previous != null) {
                    current.merge(previous);
                }

                previous = current;

                if (responsesCount % pointsInterval == 0) {
                    List<Object> dataLine = new ArrayList<>();

                    dataLine.add(ChronoUnit.SECONDS.between(startTime, localDateTime));
                    dataLine.add(responsesCount);
                    dataLine.add(current.codeCoveragePercentageFiltered(PATH_ENDPOINTS));
                    dataLine.add(current.linesExecuted(PATH_ENDPOINTS));
                    dataLine.add(current.codeCoveragePercentage());
                    dataLine.add(current.linesExecuted());

                    dataLines.add(dataLine);
                }

            } catch (IOException e) {
                e.printStackTrace();
                // TODO
            }

            task.setProgress(new BigDecimal((Math.ceil(responsesCount / filesOnDisk.size()) * 100)));
            taskService.save(this.task);
        }
    }

    private String parseTemplate() {
        // TODO Auto-generated method stub
        return null;
    }

    private static CoverageFile processFile(Path path) throws IOException {
        String fileContent = Files.readString(path, StandardCharsets.UTF_8);
        Map<String, Object> objects = JsonUtil.stringToMap(fileContent);

        CoverageFile coverageReport = new CoverageFile();

        Map<String, PhpFile> phpFiles = new HashMap<>();
        objects.entrySet().forEach(entry -> {
            phpFiles.put(entry.getKey(), processFileEntry(entry));
        });
        coverageReport.setPhpFiles(phpFiles);

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