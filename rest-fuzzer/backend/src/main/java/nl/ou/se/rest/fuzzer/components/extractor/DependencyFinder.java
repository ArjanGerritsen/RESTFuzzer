package nl.ou.se.rest.fuzzer.components.extractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.HttpMethod;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.ParameterContext;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdActionDependency;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdParameter;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdSut;
import nl.ou.se.rest.fuzzer.components.data.rmd.factory.RmdActionDependencyFactory;

public class DependencyFinder {

    // variables
    private static final String EMPTY_STRING = "";
    private static final String PATH_DEPENDENCY_FORMAT = "{%s}";
    private static final String ID_DEPENDENCIES_UNDERSCORE_ID = "_id";
    private static final List<String> PATH_DEPENDENCY_NAMES = Arrays.asList(new String[] { "id", "parent" });

    private RmdActionDependencyFactory actionDependencyFactory = new RmdActionDependencyFactory();

    private RmdSut sut;
    private Set<RmdActionDependency> actionDependencies = new TreeSet<>();

    // constructor
    public DependencyFinder(RmdSut sut) {
        this.sut = sut;
    }
    
    // methods
    public void process() {
        this.sut.getActions().forEach(a -> findRelatedActions(a, sut.getActions()));
    }

    public Set<RmdActionDependency> getActionDepencies() {
        return this.actionDependencies;
    }

    private void findRelatedActions(RmdAction action, SortedSet<RmdAction> actions) {

        List<RmdParameter> dependenciesFromPath = action.getParameters().stream().filter(p -> isDependencyFromPath(p))
                .collect(Collectors.toList());

        for (RmdParameter parameter : dependenciesFromPath) {
            addActionDependency(action, actions, parameter);
        }

        List<RmdParameter> dependenciesFromOtherEntities = action.getParameters().stream()
                .filter(p -> isDependencyFromOtherEntity(p)).collect(Collectors.toList());

        for (RmdParameter parameter : dependenciesFromOtherEntities) {
            addActionDependency(action, actions, parameter);
        }
    }

    private void addActionDependency(RmdAction action, SortedSet<RmdAction> actions, RmdParameter parameter) {
        Optional<RmdAction> actionDependsOn = findCorrespondingAction(parameter, actions);

        if (actionDependsOn.isPresent()) {
            actionDependencies.add(actionDependencyFactory.create(action, actionDependsOn.get()).build());
        } else {
            // TODO ... log.
            System.out.println(String.format("%s \t %s \t %s -> %s", action.getHttpMethod(), action.getPath(),
                    parameter.getName(), "NOT FOUND"));
        }
    }

    private Optional<RmdAction> findCorrespondingAction(RmdParameter parameter, SortedSet<RmdAction> actions) {
        String actionPath = null;

        if (isDependencyFromPath(parameter)) {
            actionPath = getActionPathFromPath(parameter);
        } else if (isDependencyFromOtherEntity(parameter)) {
            actionPath = getAchtionPathFromOtherEntity(parameter);
        }

        return findActionForNameAndHttpMethod(actionPath, HttpMethod.POST, actions);
    }

    private Optional<RmdAction> findActionForNameAndHttpMethod(String actionPath, HttpMethod httpMethod,
            SortedSet<RmdAction> actions) {
        return actions.stream().filter(a -> actionPath.equals(a.getPath()) && httpMethod == a.getHttpMethod())
                .findFirst();
    }

    private String getActionPathFromPath(RmdParameter parameter) {
        List<String> uriParts = new ArrayList<String>(Arrays.asList(parameter.getAction().getPath().split("/")));
        int idx = uriParts.indexOf(String.format(PATH_DEPENDENCY_FORMAT, parameter.getName()));
        uriParts = uriParts.subList(0, idx);
        String uri = uriParts.stream().collect(Collectors.joining("/"));
        uri = uri.replace("{parent}", "{id}");
        return uri;
    }

    private String getAchtionPathFromOtherEntity(RmdParameter parameter) {
        Predicate<String> isParameter = item -> item.indexOf("{") != -1;
        List<String> uriParts = new ArrayList<String>(Arrays.asList(parameter.getAction().getPath().split("/")));
        uriParts.removeIf(isParameter);
        uriParts.remove(uriParts.size() - 1);
        String uri = uriParts.stream().collect(Collectors.joining("/"));

        String entityName = parameter.getName().replaceAll(ID_DEPENDENCIES_UNDERSCORE_ID, EMPTY_STRING) + "s";

        return uri + "/" + entityName;
    }

    private Boolean isDependencyFromPath(RmdParameter parameter) {
        return ParameterContext.PATH == parameter.getContext() && PATH_DEPENDENCY_NAMES.contains(parameter.getName());
    }

    private Boolean isDependencyFromOtherEntity(RmdParameter parameter) {
        return parameter.getName().indexOf(ID_DEPENDENCIES_UNDERSCORE_ID) != -1;
    }
}