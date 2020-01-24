package nl.ou.se.rest.fuzzer.extractor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import nl.ou.se.rest.fuzzer.data.rmd.domain.Parameter;
import nl.ou.se.rest.fuzzer.data.rmd.domain.ParameterType;

public abstract class ExtractorUtil {

    // constants
    public static final String KEY_TYPE = "TYPE";

    private static final String ERROR_UNKOWN_PARAM_TYPE = "Unknown parametertype: %s";

    // methods
    public static Map<String, Object> getTypeAndMetas(io.swagger.models.parameters.Parameter parameter) {
        Map<String, Object> values = new HashMap<>();

        if (parameter instanceof QueryParameter) {
            QueryParameter qp = (QueryParameter) parameter;
            values.putAll(setValuesForQueryParameter(qp));

        } else if (parameter instanceof PathParameter) {
            PathParameter pp = (PathParameter) parameter;
            values.putAll(setValuesForPathParameter(pp));

        } else if (parameter instanceof FormParameter) {
            FormParameter fp = (FormParameter) parameter;
            values.putAll(setValuesForFormParameter(fp));

        } else {
            throw new IllegalAccessError(String.format(ERROR_UNKOWN_PARAM_TYPE, parameter.getClass().getName()));
        }

        putIfNotNull(values, Parameter.META_DATA_PATTERN, parameter.getPattern());

        return values;
    }

    private static Map<String, Object> setValuesForQueryParameter(QueryParameter queryParameter) {
        Map<String, Object> values = new HashMap<>();

        values.put(KEY_TYPE, getTypeForParameter(queryParameter.getType()));

        putIfNotNull(values, Parameter.META_DATA_FORMAT, queryParameter.getFormat());

        putIfNotNull(values, Parameter.META_DATA_MIN_VALUE, queryParameter.getMinimum());
        putIfNotNull(values, Parameter.META_DATA_MAX_VALUE, queryParameter.getMaximum());

        putIfNotNull(values, Parameter.META_DATA_MIN_LENGTH, queryParameter.getMinLength());
        putIfNotNull(values, Parameter.META_DATA_MAX_LENGTH, queryParameter.getMaxLength());

        putIfNotNull(values, Parameter.META_DATA_MIN_ITEMS, queryParameter.getMinItems());
        putIfNotNull(values, Parameter.META_DATA_MAX_ITEMS, queryParameter.getMaxItems());
        
        if (queryParameter.getEnum() != null) {
            putIfNotNull(values, Parameter.META_DATA_ENUM, queryParameter.getEnum().stream().collect(Collectors.joining(", ")));
        }

        putIfNotNull(values, Parameter.META_DATA_DEFAULT, queryParameter.getDefaultValue());

        return values;
    }

    private static Map<String, Object> setValuesForPathParameter(PathParameter pathParameter) {
        Map<String, Object> values = new HashMap<>();

        values.put(KEY_TYPE, getTypeForParameter(pathParameter.getType()));

        putIfNotNull(values, Parameter.META_DATA_FORMAT, pathParameter.getFormat());

        putIfNotNull(values, Parameter.META_DATA_MIN_VALUE, pathParameter.getMinimum());
        putIfNotNull(values, Parameter.META_DATA_MAX_VALUE, pathParameter.getMaximum());

        putIfNotNull(values, Parameter.META_DATA_MIN_LENGTH, pathParameter.getMinLength());
        putIfNotNull(values, Parameter.META_DATA_MAX_LENGTH, pathParameter.getMaxLength());

        putIfNotNull(values, Parameter.META_DATA_MIN_ITEMS, pathParameter.getMinItems());
        putIfNotNull(values, Parameter.META_DATA_MAX_ITEMS, pathParameter.getMaxItems());
        
        if (pathParameter.getEnum() != null) {
            putIfNotNull(values, Parameter.META_DATA_ENUM, pathParameter.getEnum().stream().collect(Collectors.joining(", ")));
        }

        putIfNotNull(values, Parameter.META_DATA_DEFAULT, pathParameter.getDefaultValue());       

        return values;
    }

    private static Map<String, Object> setValuesForFormParameter(FormParameter formParameter) {
        Map<String, Object> values = new HashMap<>();

        values.put(KEY_TYPE, getTypeForParameter(formParameter.getType()));

        putIfNotNull(values, Parameter.META_DATA_FORMAT, formParameter.getFormat());

        putIfNotNull(values, Parameter.META_DATA_MIN_VALUE, formParameter.getMinimum());
        putIfNotNull(values, Parameter.META_DATA_MAX_VALUE, formParameter.getMaximum());

        putIfNotNull(values, Parameter.META_DATA_MIN_LENGTH, formParameter.getMinLength());
        putIfNotNull(values, Parameter.META_DATA_MAX_LENGTH, formParameter.getMaxLength());

        putIfNotNull(values, Parameter.META_DATA_MIN_ITEMS, formParameter.getMinItems());
        putIfNotNull(values, Parameter.META_DATA_MAX_ITEMS, formParameter.getMaxItems());

        if (formParameter.getEnum() != null) {
            putIfNotNull(values, Parameter.META_DATA_ENUM, formParameter.getEnum().stream().collect(Collectors.joining(", ")));
        }

        putIfNotNull(values, Parameter.META_DATA_DEFAULT, formParameter.getDefaultValue());

        return values;
    }

    protected static String getTypeForParameter(String type) {
        if (type == null) {
            // default to String, for date/datetime types it may not be set
            type = ParameterType.STRING.toString();
        }
        return type;
    }

    private static void putIfNotNull(Map<String, Object> values, String key, Object value) {
        if (value != null) {
            values.put(key, value);
        }
    }
}