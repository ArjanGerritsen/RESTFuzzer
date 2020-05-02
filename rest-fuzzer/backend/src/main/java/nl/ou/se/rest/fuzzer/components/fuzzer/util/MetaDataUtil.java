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
import nl.ou.se.rest.fuzzer.components.fuzzer.executor.Authentication;
import nl.ou.se.rest.fuzzer.components.fuzzer.executor.BasicAuthentication;
import nl.ou.se.rest.fuzzer.components.shared.Constants;

public class MetaDataUtil {

    // variables
    public abstract class Meta {
        // general
        public static final String CONFIGURATION = "configuration";

        public static final String AUTHENTICATION = "authentication";
        public static final String AUTH_METHOD = "method";
        public static final String AUTH_METHOD_BASIC = "BASIC";
        public static final String AUTH_USERNAME = "username";
        public static final String AUTH_PASSWORD = "password";

        public static final String INCLUDE_ACTIONS = "includeActions";
        public static final String EXCLUDE_ACTIONS = "excludeActions";
        public static final String ACTION = "action";
        public static final String ACTION_PATH = "path";
        public static final String ACTION_METHOD = "method";

        public static final String EXCLUDE_PARAMETERS = "excludeParameters";
        public static final String PARAMETER = "parameter";
        public static final String PARAMETER_NAME = "name";

        // basic fuzzer
        public static final String REPITITIONS = "repetitions";

        // model based fuzzer
        public static final String SEQUENCE_LENGTH = "sequenceLength";
    }

    private Logger logger = LoggerFactory.getLogger(MetaDataUtil.class);

    private Map<String, Object> metaDataTuples;
    private Map<String, Object> configurationMetaDataTuples;

    // constructor
    public MetaDataUtil(Map<String, Object> metaDataTuples) {
        this.metaDataTuples = metaDataTuples;
    }

    public Boolean isValid(String... keys) {
        Boolean isValid = true;

        if (!this.metaDataTuples.containsKey(Meta.CONFIGURATION)) {
            logger.error(String.format(Constants.Fuzzer.META_DATA_MISSING, MetaDataUtil.class, Meta.CONFIGURATION));
            isValid = false;
        }

        for (String key : keys) {
            if (!this.metaDataTuples.containsKey(key)) {
                logger.error(String.format(Constants.Fuzzer.META_DATA_MISSING, MetaDataUtil.class, key));
                isValid = false;
            }
        }

        if (isValid) {
            this.configurationMetaDataTuples = ((JSONObject) this.getValue(this.metaDataTuples, Meta.CONFIGURATION))
                    .toMap();
        }

        return isValid;
    }

    @SuppressWarnings("unchecked")
    public List<RmdAction> filterActions(List<RmdAction> actions) {
        List<Map<String, String>> includeActions = (ArrayList<Map<String, String>>) this
                .getValue(this.configurationMetaDataTuples, Meta.INCLUDE_ACTIONS);
        List<Map<String, String>> excludeActions = (ArrayList<Map<String, String>>) this
                .getValue(this.configurationMetaDataTuples, Meta.EXCLUDE_ACTIONS);

        List<Map<String, Object>> excludeParameters = (ArrayList<Map<String, Object>>) this
                .getValue(this.configurationMetaDataTuples, Meta.EXCLUDE_PARAMETERS);

        if (includeActions != null && !includeActions.isEmpty()) {
            actions = actions.stream().filter(action -> isActionMatched(action, includeActions))
                    .collect(Collectors.toList());
        }

        if (excludeActions != null && !excludeActions.isEmpty()) {
            actions = actions.stream().filter(action -> !isActionMatched(action, includeActions))
                    .collect(Collectors.toList());
        }

        if (excludeParameters != null && !excludeParameters.isEmpty()) {
            actions = actions.stream().map(action -> removeMatchedParameters(action, excludeParameters))
                    .collect(Collectors.toList());
        }

        return actions;
    }

    public Integer getIntegerValue(String key) {
        return (Integer) this.getValue(this.metaDataTuples, key);
    }

    @SuppressWarnings("unchecked")
    public Authentication getAuthentication() {
        Authentication authentication = null;

        Map<String, String> authenticationMap = (Map<String, String>) this
                .getValue(this.configurationMetaDataTuples, Meta.AUTHENTICATION);

        if (authenticationMap != null && !authenticationMap.isEmpty()) {
            String method = authenticationMap.get(Meta.AUTH_METHOD);
            switch (method) {
            case Meta.AUTH_METHOD_BASIC:
                String username = authenticationMap.get(Meta.AUTH_USERNAME);
                String password = authenticationMap.get(Meta.AUTH_PASSWORD);
                authentication = new BasicAuthentication(username, password);
                break;
            default:
                logger.error(String.format(Constants.Fuzzer.META_DATA_INVALID, 1, 2));
                break;
            }
        }
        
        return authentication;
    }

    private Boolean isActionMatched(RmdAction action, List<Map<String, String>> actions) {
        for (Map<String, String> actionMap : actions) {
            if (isActionMatched(action, actionMap)) {
                return true;
            }
        }

        return false;
    }

    private Boolean isActionMatched(RmdAction action, Map<String, String> actionMap) {
        String pathRegex = (String) actionMap.get(Meta.ACTION_PATH);
        String httpMethodRegex = (String) actionMap.get(Meta.ACTION_PATH);

        if ((action.getPath().matches(pathRegex)) && (action.getHttpMethod().toString().matches(httpMethodRegex))) {
            return true;
        }

        return false;
    }

    private Boolean isParameterMatched(RmdParameter parameter, Map<String, String> parameterMap) {
        String nameRegex = (String) parameterMap.get(Meta.PARAMETER_NAME);
        return parameter.getName().matches(nameRegex);
    }

    @SuppressWarnings("unchecked")
    private RmdAction removeMatchedParameters(RmdAction action, List<Map<String, Object>> parameters) {
        List<RmdParameter> parametersToRemove = new ArrayList<RmdParameter>();
        for (RmdParameter parameter : action.getParameters()) {
            for (Map<String, Object> parameterContainer : parameters) {
                Map<String, String> actionMap = (Map<String, String>) parameterContainer.get(Meta.ACTION);
                Map<String, String> parameterMap = (Map<String, String>) parameterContainer.get(Meta.PARAMETER);

                if (isActionMatched(action, actionMap) && isParameterMatched(parameter, parameterMap)) {
                    parametersToRemove.add(parameter);
                }
            }
        }

        action.getParameters().removeAll(parametersToRemove);

        return action;
    }

    private Object getValue(Map<String, Object> metaDataTuples, String key) {
        if (!metaDataTuples.containsKey(key)) {
            return null;
        }

        Object object = metaDataTuples.get(key);

        return object;
    }
}