package nl.ou.se.rest.fuzzer.components.fuzzer.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdParameter;
import nl.ou.se.rest.fuzzer.components.service.fuz.FuzDictionaryController;
import nl.ou.se.rest.fuzzer.components.shared.Constants;

public class MetaDataUtil {

    // variables
    private Logger logger = LoggerFactory.getLogger(FuzDictionaryController.class);

    private Map<String, Object> metaDataTuples;
    private Map<String, Object> configurationMetaDataTuples;

    // constructor
    public MetaDataUtil(Map<String, Object> metaDataTuples) {
        this.metaDataTuples = metaDataTuples;
    }

    public Boolean isValid(String... keys) {
        Boolean isValid = true;

        if (!this.metaDataTuples.containsKey(Constants.Fuzzer.Meta.CONFIGURATION)) {
            logger.error(Constants.Fuzzer.META_DATA_MISSING, MetaDataUtil.class, Constants.Fuzzer.Meta.CONFIGURATION);
            isValid = false;
        }

        for (String key : keys) {
            if (!this.metaDataTuples.containsKey(key)) {
                logger.error(Constants.Fuzzer.META_DATA_MISSING, MetaDataUtil.class, key);
                isValid = false;
            }
        }

        if (isValid) {
            this.configurationMetaDataTuples = ((JSONObject) this.getValue(this.metaDataTuples,
                    Constants.Fuzzer.Meta.CONFIGURATION)).toMap();
        }

        return isValid;
    }

    @SuppressWarnings("unchecked")
    public List<RmdAction> filterActions(List<RmdAction> actions) {
        List<Map<String, String>> includeActions = (ArrayList<Map<String, String>>) this
                .getValue(this.configurationMetaDataTuples, Constants.Fuzzer.Meta.INCLUDE_ACTIONS);
        List<Map<String, String>> excludeActions = (ArrayList<Map<String, String>>) this
                .getValue(this.configurationMetaDataTuples, Constants.Fuzzer.Meta.EXCLUDE_ACTIONS);

        List<Map<String, Object>> excludeParameters = (ArrayList<Map<String, Object>>) this
                .getValue(this.configurationMetaDataTuples, Constants.Fuzzer.Meta.EXCLUDE_PARAMETERS);

        if (!includeActions.isEmpty()) {
            actions = actions.stream().filter(action -> isActionMatched(action, includeActions))
                    .collect(Collectors.toList());
        }

        if (!excludeActions.isEmpty()) {
            actions = actions.stream().filter(action -> !isActionMatched(action, includeActions))
                    .collect(Collectors.toList());
        }
        
        System.out.println("pre excludeParameters");

        if (!excludeParameters.isEmpty()) {
            actions = actions.stream().map(action -> removeMatchedParameters(action, excludeParameters))
                    .collect(Collectors.toList());
        }
        
        System.out.println("post excludeParameters");

        return actions;
    }

    public Integer getIntegerValue(String key) {
        return (Integer) this.getValue(this.metaDataTuples, key);
    }

    private Boolean isActionMatched(RmdAction action, List<Map<String, String>> actions) {
        System.out.println("pre isActionMatched");

        for (Map<String, String> actionMap : actions) {
            if (isActionMatched(action, actionMap)) {
                return true;
            }
        }

        return false;
    }

    private Boolean isActionMatched(RmdAction action, Map<String, String> actionMap) {
        System.out.println("pre isActionMatched");

        String pathRegex = (String) actionMap.get("path");
        String httpMethodRegex = (String) actionMap.get("httpMethod");

        if ((action.getPath().matches(pathRegex)) && (action.getHttpMethod().toString().matches(httpMethodRegex))) {
            return true;
        }

        return false;
    }

    private Boolean isParameterMatched(RmdParameter parameter, Map<String, String> parameterMap) {
        System.out.println("pre isParameterMatched");
        
        String nameRegex = (String) parameterMap.get("name");
        return parameter.getName().matches(nameRegex);        
    }

    @SuppressWarnings("unchecked")
    private RmdAction removeMatchedParameters(RmdAction action, List<Map<String, Object>> parameters) {
        System.out.println("pre removeMatchedParameters");

        List<RmdParameter> parametersToRemove = new ArrayList<RmdParameter>();
        for (RmdParameter parameter : action.getParameters()) {
            for (Map<String, Object> parameterContainer : parameters) {
                Map<String, String> actionMap = (Map<String, String>) parameterContainer.get("action");
                Map<String, String> parameterMap = (Map<String, String>) parameterContainer.get("parameter");

                if (isActionMatched(action, actionMap) && isParameterMatched(parameter, parameterMap)) {
                    System.out.println("removing ... " + parameter.getName());
                    parametersToRemove.add(parameter);
                    System.out.println("post removing ... ");
                }
            }
        }

        action.getParameters().removeAll(parametersToRemove);

        return action;
    }

    private Object getValue(Map<String, Object> metaDataTuples, String key) {
        if (!metaDataTuples.containsKey(key)) {
            logger.error(Constants.Fuzzer.META_DATA_MISSING, MetaDataUtil.class, key);
            return null;
        }

        Object object = metaDataTuples.get(key);

        return object;
    }
}