package nl.ou.se.rest.fuzzer.components.fuzzer;

import java.util.List;

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

@Service
public class FuzzerBasic {

    // variables
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

        for (int i = 0; i < 2; i++) { // TODO
            actions.forEach(a -> {
                FuzRequest request = ParameterUtil.requestFromAction(project, a);
                requestService.save(request);

                FuzResponse response = ExecutorUtil.getInstance().processRequest(request);
                responseService.save(response);
            });
        }
    }
}