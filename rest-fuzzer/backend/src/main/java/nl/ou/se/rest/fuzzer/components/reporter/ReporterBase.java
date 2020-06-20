package nl.ou.se.rest.fuzzer.components.reporter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReporterBase {

    protected List<Integer> getYticks(List<List<Object>> dataLines, Integer interval) {
        List<Object> lastLine = dataLines.get(dataLines.size() - 1);
        Integer max = lastLine.subList(2, lastLine.size() - 1).stream().mapToInt(v -> (Integer) v).max().getAsInt();

        return getTicks(interval, max);
    }

    protected List<Integer> getXticks(List<List<Object>> dataLines, Integer interval) {
        List<Object> lastLine = dataLines.get(dataLines.size() - 1);
        Integer max = (Integer) lastLine.get(1);

        return getTicks(interval, max);
    }

    protected List<Integer> getXticksLabels(List<List<Object>> dataLines, List<Integer> xTicks) {
        List<Integer> xTicksLabels = new ArrayList<>();

        for (Integer xTick : xTicks) {
            for (List<Object> dataLine : dataLines.subList(1, dataLines.size())) {
                Integer time = (Integer) dataLine.get(0);
                if (time.equals(xTick)) {
                    Integer numRequests = (Integer) dataLine.get(1);
                    xTicksLabels.add(numRequests);
                    break;
                }
            }
        }

        return xTicksLabels;
    }

    protected List<Integer> getTicks(Integer interval, Integer max) {
        List<Integer> ticks = new ArrayList<>();

        Integer curValue = 0;
        ticks.add(curValue);
        while (curValue <= max) {
            curValue += interval;
            ticks.add(curValue);
        }

        return ticks;
    }

    protected String ObjectListtoString(List<?> values, String seperator) {
        return values.stream().map(tick -> tick.toString()).collect(Collectors.joining(seperator));
    }
}