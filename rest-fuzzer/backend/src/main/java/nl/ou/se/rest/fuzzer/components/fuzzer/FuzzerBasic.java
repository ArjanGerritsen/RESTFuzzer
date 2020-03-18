package nl.ou.se.rest.fuzzer.components.fuzzer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzRequestService;
import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzResponseService;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzResponse;
import nl.ou.se.rest.fuzzer.components.data.rmd.dao.RmdActionService;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.components.fuzzer.util.ExecutorUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.util.ParameterUtil;
import nl.ou.se.rest.fuzzer.components.service.fuz.FuzDictionaryController;
import nl.ou.se.rest.fuzzer.components.shared.Constants;

@Service
public class FuzzerBasic {

    // variables
    private Logger logger = LoggerFactory.getLogger(FuzDictionaryController.class);

    public static final String META_DATA_REPITITIONS = "repetitions";

    private FuzProject project;

    private List<RmdAction> actions;

    @Autowired
    private RmdActionService actionService;

    @Autowired
    private FuzRequestService requestService;

    @Autowired
    private FuzResponseService responseService;

    public void start(FuzProject project) {
        this.project = project;

        actions = actionService.findBySutId(this.project.getSut().getId());

        if (!project.getMetaDataTuples().containsKey(META_DATA_REPITITIONS)) {
            logger.error(Constants.FUZZER_META_DATA_MISSING, FuzzerBasic.class, META_DATA_REPITITIONS);
            return;
        }

        Integer repititions = (Integer) project.getMetaDataTuples().get(META_DATA_REPITITIONS);
        for (int i = 0; i < (repititions + 1); i++) {
            actions.forEach(a -> {
                FuzRequest request = ParameterUtil.requestFromAction(project, a);
                requestService.save(request);

                FuzResponse response = ExecutorUtil.getInstance().processRequest(request);
                responseService.save(response);
            });
        }
    }
}