package nl.ou.se.rest.fuzzer.components.fuzzer.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzDictionaryService;
import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzRequestService;
import nl.ou.se.rest.fuzzer.components.data.fuz.dao.FuzResponseService;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzDictionary;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzRequest;
import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzResponse;
import nl.ou.se.rest.fuzzer.components.data.rmd.dao.RmdActionService;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdParameter;
import nl.ou.se.rest.fuzzer.components.data.task.domain.Task;
import nl.ou.se.rest.fuzzer.components.fuzzer.executor.ExecutorUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.metadata.MetaDataUtil;
import nl.ou.se.rest.fuzzer.components.fuzzer.metadata.MetaDataUtil.Meta;
import nl.ou.se.rest.fuzzer.components.fuzzer.util.RequestUtil;

@Service
public class FuzzerDictionary extends FuzzerBase implements Fuzzer {

    // variable(s)
    private FuzProject project = null;
    private MetaDataUtil metaDataUtil = null;
    private List<String> dictionaryValues = new ArrayList<String>();

    @Autowired
    private RmdActionService actionService;

    @Autowired
    private FuzRequestService requestService;

    @Autowired
    private FuzResponseService responseService;

    @Autowired
    private FuzDictionaryService dictionaryService;

    @Autowired
    private RequestUtil requestUtil;

    @Autowired
    private ExecutorUtil executorUtil;

    // method(s)
    public void start(FuzProject project, Task task) {
        this.project = project;

        // authentication
        executorUtil.setAuthentication(metaDataUtil.getAuthentication());

        // get meta
        Integer repetitions = metaDataUtil.getIntegerValue(Meta.REPITITIONS);
        Integer maxDictionaryParams = metaDataUtil.getIntegerValue(Meta.MAX_DICTIONARY_PARAMS);
        Integer maxDictionaryItems = metaDataUtil.getIntegerValue(Meta.MAX_DICTIONARY_ITEMS);

        List<Long> dictionaryIds = metaDataUtil.getLongArrayValues(Meta.DICTIONARIES);
        List<FuzDictionary> dictionaries = dictionaryService.findByIds(dictionaryIds);
        dictionaries.forEach(dictionary -> this.dictionaryValues.addAll(dictionary.getItems()));

        List<RmdAction> actions = actionService.findBySutId(this.project.getSut().getId());
        actions = metaDataUtil.getFilteredActions(actions);

        Integer count = 0;
        Integer total = repetitions * actions.size();

        // init requestUtil
        requestUtil.init(project, metaDataUtil.getDefaults());

        for (Integer i = 0; i < repetitions; i++) {
            for (RmdAction a : actions) {
                FuzRequest request = requestUtil.getRequestFromAction(a, null);

                List<RmdParameter> parameters = getRandomFromValues(a.getParameters(), maxDictionaryParams);
                System.out.println(parameters.size());
                for (RmdParameter parameter : parameters) {
                    System.out.println("NEW PARAM: " + parameter.getName());

                    List<String> dictionaryValues = getRandomFromValues(this.dictionaryValues, maxDictionaryItems);
                    System.out.println(dictionaryValues.size());
                    for (String dictionaryValue : dictionaryValues) {
                        System.out.println(dictionaryValue);
                        
                        FuzRequest requestCopy = request.getDeepCopy();

                        requestCopy.replaceParameterValue(parameter, dictionaryValue);

                        requestService.save(requestCopy);

                        FuzResponse response = executorUtil.processRequest(requestCopy);
                        responseService.save(response);
                    }
                }

                count++;
                saveTaskProgress(task, count, total);
            }
        }
    }

    public List<RmdParameter> getRandomFromValues(SortedSet<RmdParameter> values, Integer max) {
        List<RmdParameter> list = values.stream().collect(Collectors.toList());
        Collections.shuffle(list);
        Integer toIndex = Arrays.asList(list.size(), max).stream().min(Integer::compare).get();
        return list.subList(0, toIndex);
    }

    public List<String> getRandomFromValues(List<String> values, Integer max) {
        Collections.shuffle(values);
        Integer toIndex = Arrays.asList(values.size(), max).stream().min(Integer::compare).get();
        return values.subList(0, toIndex);
    }

    public Boolean isMetaDataValid(Map<String, Object> metaDataTuples) {
        this.metaDataUtil = new MetaDataUtil(metaDataTuples);
        return metaDataUtil.isValid(Meta.CONFIGURATION, Meta.REPITITIONS, Meta.DICTIONARIES, Meta.MAX_DICTIONARY_PARAMS,
                Meta.MAX_DICTIONARY_ITEMS);
    }
}