package nl.ou.se.rest.fuzzer.components.fuzzer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

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
import nl.ou.se.rest.fuzzer.components.fuzzer.util.MetaDataUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.util.RequestUtil;
import nl.ou.se.rest.fuzzer.components.shared.Constants;

@Service
public class FuzzerBasic implements Fuzzer {

    // variables
    private FuzProject project = null;
    private MetaDataUtil metaDataUtil = null;

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

    public void start(FuzProject project, Task task) {
        this.project = project;

        List<RmdAction> actions = actionService.findBySutId(this.project.getSut().getId());
        actions = metaDataUtil.filterActions(actions);

        Integer repetitions = metaDataUtil.getIntegerValue(Constants.Fuzzer.Meta.REPITITIONS);

        int count = 0;
        int total = repetitions * actions.size();

        for (int i = 0; i < repetitions; i++) {
            for (RmdAction a : actions) {
                FuzRequest request = requestUtil.getRequestFromAction(project, a);
                requestService.save(request);

                FuzResponse response = executorUtil.processRequest(request);
                responseService.save(response);

                count++;
                doProgress(task, count, total);
            }
        }
    }

    private void doProgress(Task task, int count, int total) {
        BigDecimal progress = BigDecimal.valueOf(count).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        if (task.getProgress() == null || task.getProgress().compareTo(progress) < 0) {
            task.setProgress(progress);
            taskService.save(task);
        }
    }

    public Boolean isMetaDataValid(Map<String, Object> metaDataTuples) {
        this.metaDataUtil = new MetaDataUtil(metaDataTuples);
        return metaDataUtil.isValid(Constants.Fuzzer.Meta.REPITITIONS);
    }
}