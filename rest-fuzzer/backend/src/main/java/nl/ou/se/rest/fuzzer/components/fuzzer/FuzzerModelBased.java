package nl.ou.se.rest.fuzzer.components.fuzzer;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzRequestService;
import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzResponseService;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzResponse;
import nl.ou.se.rest.fuzzer.components.data.rmd.dao.RmdActionDependencyService;
import nl.ou.se.rest.fuzzer.components.data.rmd.dao.RmdActionService;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdActionDependency;
import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;
import nl.ou.se.rest.fuzzer.components.fuzzer.util.ExecutorUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.util.MetaDataUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.util.RequestUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.util.SequenceUtil;
import nl.ou.se.rest.fuzzer.components.shared.Constants;

@Service
public class FuzzerModelBased extends FuzzerBase implements Fuzzer {

    // variables
    private FuzProject project = null;
    private MetaDataUtil metaDataUtil = null;

    @Autowired
    private RmdActionService actionService;

    @Autowired
    private RmdActionDependencyService actionDependencyService;

    @Autowired
    private FuzRequestService requestService;

    @Autowired
    private FuzResponseService responseService;

    @Autowired
    private RequestUtil requestUtil;

    @Autowired
    private ExecutorUtil executorUtil;

    public void start(FuzProject project, Task task) {
        this.project = project;

        List<RmdAction> actions = actionService.findBySutId(this.project.getSut().getId());
        actions = metaDataUtil.filterActions(actions);

        List<RmdActionDependency> dependencies = actionDependencyService.findBySutId(this.project.getSut().getId());

        Integer sequenceLength = metaDataUtil.getIntegerValue(Constants.Fuzzer.Meta.SEQUENCE_LENGTH);

        SequenceUtil sequenceUtil = new SequenceUtil(actions, dependencies);
        List<String> sequences = sequenceUtil.getValidSequences(sequenceLength);

        int count = 0;
        int total = sequences.size();

        // for all sequences
        for (String sequence : sequences) {
            List<RmdAction> actionsFromSequence = sequenceUtil.getActionsFromSequence(sequence);

            // for each action in sequence
            for (RmdAction a : actionsFromSequence) {
                FuzRequest request = requestUtil.getRequestFromAction(project, a);
                requestService.save(request);

                FuzResponse response = executorUtil.processRequest(request);
                responseService.save(response);
            }

            count++;
            saveTaskProgress(task, count, total);            
        }
    }

    public Boolean isMetaDataValid(Map<String, Object> metaDataTuples) {
        this.metaDataUtil = new MetaDataUtil(metaDataTuples);
        return metaDataUtil.isValid(Constants.Fuzzer.Meta.SEQUENCE_LENGTH);
    }
}