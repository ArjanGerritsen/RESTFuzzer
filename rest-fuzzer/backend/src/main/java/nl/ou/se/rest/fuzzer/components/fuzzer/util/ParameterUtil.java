package nl.ou.se.rest.fuzzer.components.fuzzer.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdParameter;
import nl.ou.se.rest.fuzzer.components.shared.Constants;

@Service
public class ParameterUtil {

    // variables
    private static Logger logger = LoggerFactory.getLogger(ParameterUtil.class);

    private static final String FORMAT_IP = "ip";
    private static final String FORMAT_URI = "uri";
    private static final String FORMAT_EMAIL = "email";
    private static final String FORMAT_DATETIME = "date-time";

    private static final String FORMAT_INT64 = "int64";

    // methods
    public Map<String, Object> createParameterMap(List<RmdParameter> parameters) {
        Map<String, Object> parameterMap = new HashMap<>();

        parameters.forEach(parameter -> parameterMap.put(parameter.getName(), getValueForParameter(parameter)));

        return parameterMap;
    }

    private Object getValueForParameter(RmdParameter parameter) {
        switch (parameter.getType()) {
        case BOOLEAN:
            return getBooleanValue(parameter);
        case STRING:
            return getStringValue(parameter);
        case INTEGER:
            return getIntegerValue(parameter);
        case ARRAY:
            return getArrayValue(parameter);
        default:
            logger.error(String.format(Constants.Fuzzer.PARAMETER_TYPE_UNKNOWN, parameter.getType()));
            return "?";
        }
    }

    private Object getBooleanValue(RmdParameter parameter) {
        return RandomUtils.nextBoolean();
    }

    private Object getStringValue(RmdParameter parameter) {
        if (parameter.getMetaDataTuples().containsKey(RmdParameter.META_DATA_FORMAT)) {
            String format = (String) parameter.getMetaDataTuples().get(RmdParameter.META_DATA_FORMAT);

            switch (format) {
            case FORMAT_IP:
                return "127.0.0.1";
            case FORMAT_URI:
                return "/uri";
            case FORMAT_EMAIL:
                return "test@test.nl";
            case FORMAT_DATETIME:
                return "2020-01-01 00:00:00";
            default:
                logger.error(String.format(Constants.Fuzzer.META_DATA_INVALID, RmdParameter.META_DATA_FORMAT, format));
            }
        }

        return "abc";
    }

    private Object getIntegerValue(RmdParameter parameter) {
        if (parameter.getMetaDataTuples().containsKey(RmdParameter.META_DATA_FORMAT)) {
            String format = (String) parameter.getMetaDataTuples().get(RmdParameter.META_DATA_FORMAT);
            int min = (int) parameter.getMetaDataTupleValue(RmdParameter.META_DATA_MIN_VALUE, 1);
            int max = (int) parameter.getMetaDataTupleValue(RmdParameter.META_DATA_MAX_VALUE, 1) + 1;

            switch (format) {
            case FORMAT_INT64:
                return RandomUtils.nextInt(min, max);
            default:
                logger.error(String.format(Constants.Fuzzer.META_DATA_INVALID, RmdParameter.META_DATA_FORMAT, format));
            }
        }

        return 1;
    }
    
    private Object getArrayValue(RmdParameter parameter) {
        // TODO
        String format = null;
        if (parameter.getMetaDataTuples().containsKey(RmdParameter.META_DATA_ARRAY_FORMAT)) {
            format = (String) parameter.getMetaDataTuples().get(RmdParameter.META_DATA_ARRAY_FORMAT);
        }

        // TODO
        if (parameter.getMetaDataTuples().containsKey(RmdParameter.META_DATA_ARRAY_TYPE)) {
            String type = (String) parameter.getMetaDataTuples().get(RmdParameter.META_DATA_ARRAY_TYPE);
        }


        if (parameter.getMetaDataTuples().containsKey(RmdParameter.META_DATA_ARRAY_ENUM)) {
            String values = (String) parameter.getMetaDataTuples().get(RmdParameter.META_DATA_ARRAY_ENUM);
            String[] valuesArray = values.split(Constants.VALUE_SEPERATOR);
            String value = valuesArray[RandomUtils.nextInt(0, valuesArray.length)];
            return value;
        }

        return "abc";
    }
}