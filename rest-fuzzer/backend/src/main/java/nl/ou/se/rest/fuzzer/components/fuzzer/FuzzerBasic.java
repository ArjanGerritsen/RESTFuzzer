package nl.ou.se.rest.fuzzer.components.fuzzer;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import nl.ou.se.rest.fuzzer.components.data.task.dao.TaskService;
import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;
import nl.ou.se.rest.fuzzer.components.fuzzer.util.ExecutorUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.util.RequestUtil;
import nl.ou.se.rest.fuzzer.components.service.fuz.FuzDictionaryController;
import nl.ou.se.rest.fuzzer.components.shared.Constants;

@Service
public class FuzzerBasic {

    // variables
    private Logger logger = LoggerFactory.getLogger(FuzDictionaryController.class);

    private List<RmdAction> actions;

    @Autowired
    private RmdActionService actionService;

    @Autowired
    private FuzRequestService requestService;

    @Autowired
    private FuzResponseService responseService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RequestUtil requestUtil;

    @Autowired
    private ExecutorUtil executorUtil;

    private Integer repititions = null;

    public void start(FuzProject project, Task task) {
        if (!collectMetaData(project)) {
            return;
        }

        int total = repititions * actions.size();
        int count = 0;

        for (int i = 0; i < repititions; i++) {
            for (RmdAction a : actions) {
                FuzRequest request = requestUtil.getRequestFromAction(project, a);
                requestService.save(request);

                FuzResponse response = executorUtil.processRequest(request);
                responseService.save(response);

                count++;
                BigDecimal progress = BigDecimal.valueOf(count)
                        .divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                if (task.getProgress() == null || task.getProgress().compareTo(progress) < 0) {
                    task.setProgress(progress);
                    taskService.save(task);
                }
            }
        }
    }

    public Boolean collectMetaData(FuzProject project) {
        Boolean allMetaDataComplete = true;

        actions = actionService.findBySutId(project.getSut().getId());

        if (!project.getMetaDataTuples().containsKey(Constants.Fuzzer.Meta.REPITITIONS)) {
            allMetaDataComplete = false;
            logger.error(Constants.Fuzzer.META_DATA_MISSING, FuzzerBasic.class, Constants.Fuzzer.Meta.REPITITIONS);
        }

        this.repititions = (Integer) project.getMetaDataTuples().get(Constants.Fuzzer.Meta.REPITITIONS);

        return allMetaDataComplete;
    }
}