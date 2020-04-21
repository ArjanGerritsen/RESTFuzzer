package nl.ou.se.rest.fuzzer.components.fuzzer.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdActionDependency;

public class SequenceUtil {

    // variables
    private Logger logger = LoggerFactory.getLogger(SequenceUtil.class);

    private List<RmdAction> actions = new ArrayList<>();
    private List<RmdActionDependency> dependencies = new ArrayList<>();

    private Map<Long, List<Long>> mappedDepencies = new HashMap<>();

    public SequenceUtil(List<RmdAction> actions, List<RmdActionDependency> dependencies) {
        this.actions = actions;

        System.out.println("SIZE ACTIONS: " + this.actions.size());

        this.dependencies = dependencies;
        this.init();
    }

    // methods
    public List<String> getValidSequences(Integer sequenceLength) {
        List<String> sequences = new ArrayList<String>();
        for (int sequence = 1; sequence <= sequenceLength; sequence++) {
            sequences = this.createNewSequences(sequences);
        }

        
        
        System.out.println("SIZE SEQUENCES: " + sequences.size());

//        for (String sequence : sequences) {
//            System.out.println(sequence);
//        }

        return sequences;
    }

    private void init() {
        dependencies.forEach(d -> {
            List<Long> dependsOnActionIds = new ArrayList<>();
            Long actionId = d.getAction().getId();
            if (this.mappedDepencies.containsKey(actionId)) {
                dependsOnActionIds = this.mappedDepencies.get(actionId);
            }
            dependsOnActionIds.add(d.getActionDependsOn().getId());
            this.mappedDepencies.put(actionId, dependsOnActionIds);
        });
    }

    private List<String> createNewSequences(List<String> sequences) {
        List<String> newSequences = new ArrayList<String>();
        if (sequences.isEmpty()) {
            newSequences = this.actions.stream().map(a -> a.getId().toString()).filter(s -> satisfiesAllDependencies(s))
                    .collect(Collectors.toList());
        } else {
            for (String sequence : sequences) {
                for (RmdAction action : this.actions) {
                    String newSequence = sequence.concat("," + action.getId().toString());
                    if (satisfiesAllDependencies(newSequence)) {
                        newSequences.add(newSequence);
                    }
                }
            }
        }

        sequences.addAll(newSequences);

        return sequences;
    }

    private Boolean satisfiesAllDependencies(String sequence) {
        String[] actionStringIds = sequence.split(",");
        List<Long> actionIds = Arrays.asList(actionStringIds).stream().map(id -> Long.parseLong(id))
                .collect(Collectors.toList());

        // only check last item, the sequence -1 is already checked ant thus valid
        if (!satisfiesDependenciesForLastItem(actionIds)) {
            return false;
        }

        return true;
    }

    private Boolean satisfiesDependenciesForLastItem(List<Long> actionIds) {
        Long actionId = actionIds.get(actionIds.size() - 1);

        List<Long> requiredDependencies = new ArrayList<>();
        if (this.mappedDepencies.containsKey(actionId)) {
            requiredDependencies = this.mappedDepencies.get(actionId);
        }

        if (!requiredDependencies.isEmpty() && actionIds.size() > 1) {
            List<Long> dependenciesInSequence = actionIds.subList(0, actionIds.size() - 1);
            requiredDependencies.removeIf(d -> dependenciesInSequence.contains(d));
        }

        Boolean satisFiesDependencies = requiredDependencies == null || requiredDependencies.isEmpty();

        logger.info(
                String.format("Last Action with id %s from sequence %s has dependencies %s and dependencies are satisfied: %s ",
                        actionId, actionIds, requiredDependencies, satisFiesDependencies));

        return satisFiesDependencies;
    }
}