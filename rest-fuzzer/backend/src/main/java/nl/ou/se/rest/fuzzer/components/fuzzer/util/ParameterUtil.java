package nl.ou.se.rest.fuzzer.components.fuzzer.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.components.data.fuz.factory.FuzRequestFactory;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.ParameterContext;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdParameter;

public class ParameterUtil {

    private static FuzRequestFactory requestFactory = new FuzRequestFactory();

    public static FuzRequest requestFromAction(FuzProject project, RmdAction action) {
        requestFactory.create(project, action.getPath(), action.getHttpMethod());

        for (ParameterContext context : ParameterContext.values()) {
            requestFactory.addParameterMap(context, createParameterMap(action.getParametersByContext(context)));
        }

        return requestFactory.build();
    }

    private static Map<String, Object> createParameterMap(List<RmdParameter> parameters) {
        Map<String, Object> parameterMap = new HashMap<>();

        parameters.forEach(parameter -> parameterMap.put(parameter.getName(), getValueForParameter(parameter)));

        return parameterMap;
    }

    private static Object getValueForParameter(RmdParameter parameter) {
        switch (parameter.getType()) {
        case BOOLEAN:
            return true;
        case STRING:
            return "abc";
        case INTEGER:
            return 1;
        case ARRAY:
            return null;
        default:
            return "?"; // TODO dit zou niet voor mogen komen + zinvolle waarden fuzzen ...
        }
    }
}