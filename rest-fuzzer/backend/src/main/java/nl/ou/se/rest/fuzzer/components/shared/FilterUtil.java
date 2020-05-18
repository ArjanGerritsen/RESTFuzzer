package nl.ou.se.rest.fuzzer.components.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.springframework.data.domain.PageRequest;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzSequenceStatus;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.DiscoveryModus;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.HttpMethod;

public abstract class FilterUtil {

    // variables
    public static final String PATH = "path";

    private static final String HTTP_METHODS = "httpMethods";
    private static final String DISCOVERY_MODES = "discoveryModes";
    private static final String HTTP_RESPONSE_RANGES = "httpResponseRanges";
    private static final Object SEQUENCE_STATUSES = "sequenceStatuses";
    private static final Object SEQUENCE_LENGTHS = "sequenceLengths";

    // methods
    public static String toLike(String query) {
        if (StringUtils.isAllBlank(query)) {
            return "%";
        } else {
            return String.format("%%%s%%", query);
        }
    }

    public static PageRequest toPageRequest(int curPage, int perPage) {
        return PageRequest.of(curPage - 1, perPage);
    }

    public static List<HttpMethod> getHttpMethods(String filter) {
        List<HttpMethod> httpMethods = new ArrayList<>(Arrays.asList(HttpMethod.values()));
        if (filter != null) {
            Map<String, Object> items = JsonUtil.stringToMap(filter);
            if (items.get(HTTP_METHODS) != null) {
                httpMethods.clear();
                JSONArray httpMethodsArray = (JSONArray) items.get(HTTP_METHODS);
                httpMethodsArray.forEach(o -> {
                    httpMethods.add(HttpMethod.valueOf(o.toString()));
                });
            }
        }
        return httpMethods;
    }

    public static List<Integer> getHttpResponseCodes(List<Integer> defaultValues, String filter) {
        List<Integer> values = new ArrayList<>(defaultValues);
        if (filter != null) {
            values.clear();
            Map<String, Object> items = JsonUtil.stringToMap(filter);
            if (items.get(HTTP_RESPONSE_RANGES) != null) {
                JSONArray rangesArray = (JSONArray) items.get(HTTP_RESPONSE_RANGES);
                rangesArray.forEach(o -> {
                    int startRange = getStartFromRange(o.toString());
                    values.addAll(defaultValues.stream().filter(v -> v >= startRange && v <= (startRange + 99))
                            .collect(Collectors.toList()));
                });
            }
        }
        return values;
    }

    public static List<Integer> getLengths(String filter) {
        List<Integer> values = IntStream.range(1, 9).boxed().collect(Collectors.toList());
        if (filter != null) {
            values.clear();
            Map<String, Object> items = JsonUtil.stringToMap(filter);
            if (items.get(SEQUENCE_LENGTHS) != null) {
                JSONArray lengthsArray = (JSONArray) items.get(SEQUENCE_LENGTHS);
                lengthsArray.forEach(o -> {
                    values.add((Integer) o);
                });
            }
        }

        return values;
    }

    public static List<FuzSequenceStatus> getStatuses(String filter) {
        List<FuzSequenceStatus> statuses = new ArrayList<>(Arrays.asList(FuzSequenceStatus.values()));
        if (filter != null) {
            Map<String, Object> items = JsonUtil.stringToMap(filter);
            if (items.get(SEQUENCE_STATUSES) != null) {
                statuses.clear();
                JSONArray statusesArray = (JSONArray) items.get(SEQUENCE_STATUSES);
                statusesArray.forEach(o -> {
                    statuses.add(FuzSequenceStatus.valueOf(o.toString()));
                });
            }
        }
        return statuses;
    }

    private static int getStartFromRange(String range) {
        switch (range) {
        case "-":
            return 0;
        case "2xx":
            return 200;
        case "3xx":
            return 300;
        case "4xx":
            return 400;
        case "5xx":
            return 500;
        default:
            return 0;
        }
    }

    public static List<DiscoveryModus> getDiscoveryModes(String filter) {
        List<DiscoveryModus> discoveryModes = new ArrayList<>(Arrays.asList(DiscoveryModus.values()));
        if (filter != null) {
            Map<String, Object> items = JsonUtil.stringToMap(filter);
            if (items.get(DISCOVERY_MODES) != null) {
                discoveryModes.clear();
                JSONArray discoveryModesArray = (JSONArray) items.get(DISCOVERY_MODES);
                discoveryModesArray.forEach(o -> {
                    discoveryModes.add(DiscoveryModus.valueOf(o.toString()));
                });
            }
        }
        return discoveryModes;
    }

    public static String getValueFromFilter(String filter, String key) {
        String value = "";
        if (filter != null) {
            Map<String, Object> items = JsonUtil.stringToMap(filter);
            if (items.get(key) != null) {
                value = items.get(key).toString();
            }
        }
        return value;
    }
}