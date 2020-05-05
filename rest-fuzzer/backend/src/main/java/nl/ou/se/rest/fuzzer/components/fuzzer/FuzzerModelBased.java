package nl.ou.se.rest.fuzzer.components.fuzzer;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzRequestService;
import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzResponseService;
import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzSequenceService;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzResponse;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzSequence;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzSequenceStatus;
import nl.ou.se.rest.fuzzer.components.data.fuz.factory.FuzSequenceFactory;
import nl.ou.se.rest.fuzzer.components.data.rmd.dao.RmdActionDependencyService;
import nl.ou.se.rest.fuzzer.components.data.rmd.dao.RmdActionService;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdActionDependency;
import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;
import nl.ou.se.rest.fuzzer.components.fuzzer.executor.ExecutorUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.util.MetaDataUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.util.RequestUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.util.SequenceUtil;

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
    private FuzSequenceService sequenceService;

    @Autowired
    private RequestUtil requestUtil;

    @Autowired
    private ExecutorUtil executorUtil;

    private FuzSequenceFactory sequenceFactory = new FuzSequenceFactory();

    public void start(FuzProject project, Task task) {
        this.project = project;

        // get meta
        Integer maxSequenceLength = metaDataUtil.getIntegerValue(MetaDataUtil.Meta.MAX_SEQUENCE_LENGTH);
        Integer maxNumRequests = metaDataUtil.getIntegerValue(MetaDataUtil.Meta.MAX_NUMBER_REQUESTS);

        // authentication
        executorUtil.setAuthentication(metaDataUtil.getAuthentication());

        // get sequences
        List<RmdAction> actions = actionService.findBySutId(this.project.getSut().getId());
        actions = metaDataUtil.filterActions(actions);

        List<RmdActionDependency> dependencies = actionDependencyService.findBySutId(this.project.getSut().getId());

        SequenceUtil sequenceUtil = new SequenceUtil(actions, dependencies);
        List<String> sequences = sequenceUtil.getValidSequences(maxSequenceLength);

        int count = 0;
        int total = sequenceUtil.getNumberOfRequests(sequences);

        // cap at maxNumRequests
        if (total > maxNumRequests) {
            sequences = sequenceUtil.getRandomSequences(sequences, maxNumRequests);
            total = maxNumRequests;
        }

        // for all sequences
        int sequencePosition = 1;
        for (String sequenceString : sequences) {
            List<RmdAction> actionsFromSequence = sequenceUtil.getActionsFromSequence(sequenceString);

            FuzSequence sequence = sequenceFactory.create(sequencePosition, actionsFromSequence.size(), project).build();
            sequenceService.save(sequence);

            // create requests for sequence
            for (RmdAction a : actionsFromSequence) {
                FuzRequest request = requestUtil.getRequestFromAction(project, a);
                requestService.save(request);
                sequence.addRequest(request);
            }

            sequence = sequenceService.save(sequence);

            // execute requests for sequence
            for (FuzRequest r : sequence.getRequests()) {
                FuzResponse response = executorUtil.processRequest(r);
                responseService.save(response);

                count++;                
            }

            // update sequence
            sequence.setStatus(FuzSequenceStatus.COMPLETE);
            sequenceService.save(sequence);

            sequencePosition++;
            saveTaskProgress(task, count, total);
        }
    }

    public Boolean isMetaDataValid(Map<String, Object> metaDataTuples) {
        this.metaDataUtil = new MetaDataUtil(metaDataTuples);
        return metaDataUtil.isValid(MetaDataUtil.Meta.MAX_SEQUENCE_LENGTH, MetaDataUtil.Meta.MAX_NUMBER_REQUESTS);
    }
}