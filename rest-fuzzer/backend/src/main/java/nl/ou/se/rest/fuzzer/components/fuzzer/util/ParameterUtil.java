package nl.ou.se.rest.fuzzer.components.fuzzer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzSequence;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.ParameterContext;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.ParameterType;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdParameter;
import nl.ou.se.rest.fuzzer.components.fuzzer.metadata.ConfigurationParameter;
import nl.ou.se.rest.fuzzer.components.shared.Constants;

@Service
public class ParameterUtil {

    // variable(s)
    private static Logger logger = LoggerFactory.getLogger(ParameterUtil.class);

    private static final String FORMAT_IP = "ip";
    private static final String FORMAT_URI = "uri";
    private static final String FORMAT_EMAIL = "email";
    private static final String FORMAT_DATETIME = "date-time";

    private static final String FORMAT_INT64 = "int64";

    @Autowired
    private DependencyUtil dependencyUtil;

    private Map<ConfigurationParameter, Object> defaults;

    // method(s)
    public void init(Map<ConfigurationParameter, Object> defaults) {
        this.defaults = defaults;
    }

    public Map<String, Object> createParameterMap(List<RmdParameter> parameters, FuzSequence sequence) {
        Map<String, Object> parameterMap = new HashMap<>();

        parameters.forEach(parameter -> {
            parameterMap.put(parameter.getName(), getValueForParameter(parameter, sequence));
        });

        return parameterMap;
    }

    private Object getValueForParameter(RmdParameter parameter, FuzSequence sequence) {
        Object defaultValue = getDefaultValue(parameter);
        if (defaultValue != null) {
            return defaultValue;
        }

        // model-based (dictionary) - context from previous requests in sequence
        if (sequence != null && dependencyUtil.hasDependency(parameter, sequence)) {
            return dependencyUtil.getValueFromPreviousRequestInSequence(parameter, sequence);
        }

        if (ParameterType.ARRAY.equals(parameter.getType())) {
            return getArrayValue(parameter);
        } else {
            String format = (String) parameter.getMetaDataTuples().get(RmdParameter.META_DATA_FORMAT);
            return getValueForParameter(parameter, parameter.getType(), format);
        }
    }

    private Object getDefaultValue(RmdParameter parameter) {
        if (this.defaults == null) {
            return null;
        }

        Optional<Entry<ConfigurationParameter, Object>> entry = this.defaults.entrySet().stream()
                .filter(cp -> cp.getKey().matches(parameter)).limit(1).findFirst();

        if (entry.isPresent()) {
            return entry.get().getValue();
        }
        return null;
    }

    private Object getValueForParameter(RmdParameter parameter, ParameterType type, String format) {
        switch (type) {
        case BOOLEAN:
            return getBooleanValue(parameter);
        case STRING:
            return getStringValue(parameter, format);
        case INTEGER:
            return getIntegerValue(parameter, format);
        default:
            logger.error(String.format(Constants.Fuzzer.PARAMETER_TYPE_UNKNOWN, parameter.getType()));
            return "?";
        }
    }

    private Object getArrayValue(RmdParameter parameter) {
        List<Object> list = new ArrayList<>();

        if (parameter.getMetaDataTuples().containsKey(RmdParameter.META_DATA_ARRAY_ENUM)) {
            String values = (String) parameter.getMetaDataTuples().get(RmdParameter.META_DATA_ARRAY_ENUM);
            String[] valuesArray = values.split(Constants.VALUE_SEPERATOR);
            return valuesArray[RandomUtils.nextInt(0, valuesArray.length)];
        } else {
            ParameterType type = null;
            if (parameter.getMetaDataTuples().containsKey(RmdParameter.META_DATA_ARRAY_TYPE)) {
                type = ParameterType.valueOf(
                        ((String) parameter.getMetaDataTuples().get(RmdParameter.META_DATA_ARRAY_TYPE)).toUpperCase());
            }

            String format = null;
            if (parameter.getMetaDataTuples().containsKey(RmdParameter.META_DATA_ARRAY_FORMAT)) {
                format = (String) parameter.getMetaDataTuples().get(RmdParameter.META_DATA_ARRAY_FORMAT);
            }

            Object value = getValueForParameter(parameter, type, format);
            list.add(value);
        }

        if (ParameterContext.QUERY.equals(parameter.getContext())) {
            return list.stream().map(o -> o.toString()).collect(Collectors.joining());
        }

        return list.toArray();
    }

    private Object getBooleanValue(RmdParameter parameter) {
        return RandomUtils.nextBoolean();
    }

    private Object getStringValue(RmdParameter parameter, String format) {
        if (parameter.getMetaDataTuples().containsKey(RmdParameter.META_DATA_FORMAT)) {
            switch (format) {
            case FORMAT_IP:
                return "127.0.0.1";
            case FORMAT_URI:
                return "/uri";
            case FORMAT_EMAIL:
                return generateRandomValidEmail();
            case FORMAT_DATETIME:
                return "2020-01-01 00:00:00";
            default:
                logger.error(String.format(Constants.Fuzzer.META_DATA_INVALID, RmdParameter.META_DATA_FORMAT, format));
            }
        }

        return RandomStringUtils.randomAlphabetic(5);
    }

    private String generateRandomValidEmail() {
        return String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(),
                RandomStringUtils.randomAlphabetic(5).toLowerCase(),
                RandomStringUtils.randomAlphabetic(2).toLowerCase());
    }

    private Object getIntegerValue(RmdParameter parameter, String format) {
        if (parameter.getMetaDataTuples().containsKey(RmdParameter.META_DATA_FORMAT)) {
            int min = (int) parameter.getMetaDataTupleValue(RmdParameter.META_DATA_MIN_VALUE, 1);
            int max = (int) parameter.getMetaDataTupleValue(RmdParameter.META_DATA_MAX_VALUE, 1) + 1;

            switch (format) {
            case FORMAT_INT64:
                return RandomUtils.nextInt(min, max);
            default:
                logger.error(String.format(Constants.Fuzzer.META_DATA_INVALID, RmdParameter.META_DATA_FORMAT, format));
            }
        }

        return RandomUtils.nextInt(1, 10);
    }
}