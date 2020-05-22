package nl.ou.se.rest.fuzzer.components.fuzzer.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzSequence;
import nl.ou.se.rest.fuzzer.components.data.fuz.factory.FuzRequestFactory;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.ParameterContext;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;

@Service
public class RequestUtil {

    // variable(s)
    private static FuzRequestFactory requestFactory = new FuzRequestFactory();

    @Autowired
    private ParameterUtil parameterUtil;

    // method(s)
    public FuzRequest getRequestFromAction(FuzProject project, RmdAction action, FuzSequence sequence) {
        requestFactory.create(project, action, action.getPath(), action.getHttpMethod());

        for (ParameterContext context : ParameterContext.values()) {
            requestFactory.addParameterMap(context,
                    parameterUtil.createParameterMap(action.getParametersByContext(context), sequence));
        }

        return requestFactory.build();
    }
}