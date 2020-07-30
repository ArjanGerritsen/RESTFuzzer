package nl.ou.se.rest.fuzzer.components.reporter.responses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class DataTable {

    private SortedMap<DataTableId, List<Object[]>> data = new TreeMap<>();

    public void add(Integer requests, Long seconds, List<Object[]> statusCodesAndCounts) {
        this.data.put(new DataTableId(requests, seconds), statusCodesAndCounts);
    }

    public List<List<Object>> getDataLines(List<Integer> statusCodes) {
        List<List<Object>> dataLines = new ArrayList<>();

        // values
        SortedMap<Integer, Integer> statusCodesTotals = new TreeMap<>();

        for (Entry<DataTableId, List<Object[]>> entry : this.data.entrySet()) {
            List<Object> dataLine = new ArrayList<>();

            dataLine.add(entry.getKey().getNumRequests()); // requests
            dataLine.add(entry.getKey().getSeconds()); // seconds

            for (Integer statusCode : statusCodes) {
                Integer statusCodeCount = entry.getValue().stream().filter(statusAndCount -> {
                    return statusAndCount[0].equals(statusCode);
                }).mapToInt(statusAndCount -> {
                    return Long.valueOf((long) statusAndCount[1]).intValue();
                }).sum();

                if (statusCodesTotals.containsKey(statusCode)) {
                    statusCodeCount += statusCodesTotals.get(statusCode);
                }
                statusCodesTotals.put(statusCode, statusCodeCount);

                // cumulative response count for current response type
                dataLine.add(statusCodeCount);
            }

            dataLines.add(dataLine);
        }

        return dataLines;
    }

    public void reset() {
        this.data.clear();
    }

}