package nl.ou.se.rest.fuzzer.components.extractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Collectors;

import nl.ou.se.rest.fuzzer.components.data.rmd.domain.HttpMethod;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.ParameterContext;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdAction;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdParameter;
import nl.ou.se.rest.fuzzer.components.data.rmd.domain.RmdSut;

public class RelationFinder {

    // variables
    private static final String ID_DEPENDENCIES_UNDERSCORE_ID = "_id";

    private static final String PATH_DEPENDENCY_FORMAT = "{%s}";

    private static final List<String> PATH_DEPENDENCY_NAMES = Arrays.asList(new String[] { "id", "parent" });

    private RmdSut sut;

    // constructor
    public RelationFinder(RmdSut sut) {
        this.sut = sut;
    }

    public void process() {
        this.sut.getActions().forEach(a -> findRelatedActions(a, sut.getActions()));
    }

    private void findRelatedActions(RmdAction action, SortedSet<RmdAction> actions) {

        List<RmdParameter> dependenciesFromPath = action.getParameters().stream().filter(p -> isDependencyFromPath(p))
                .collect(Collectors.toList());

        for (RmdParameter parameter : dependenciesFromPath) {
            Optional<RmdAction> actionDependency = findCorrespondingAction(parameter, actions);

            if (actionDependency.isPresent()) {
                System.out.println(String.format("%s \t %s \t %s -> %s", action.getHttpMethod(), action.getPath(), parameter.getName(), actionDependency.get().getPath()));
            } else {
                // TODO ... log.
                System.out.println(String.format("%s \t %s \t %s -> %s", action.getHttpMethod(), action.getPath(), parameter.getName(), "NOT FOUND"));
            }
        }

//        List<RmdParameter> dependenciesNotFromPath = action.getParameters().stream()
//                .filter(p -> isDependencyToOther(p)).collect(Collectors.toList());
//
//        for (RmdParameter parameter : dependenciesNotFromPath) {
//            RmdAction actionDependency = findCorrespondingAction(parameter, actions);
//
//            System.out.println(String.format("%s \t %s \t %s -> %s", action.getHttpMethod(), action.getPath(),
//                    parameter.getName(), actionDependency.getPath()));
//        }
    }

    private Optional<RmdAction> findCorrespondingAction(RmdParameter parameter, SortedSet<RmdAction> actions) {
        String actionPath = null;

        if (isDependencyFromPath(parameter)) {
            actionPath = getDependencyFromPath(parameter);
//        } else if (isDependencyToOther(parameter)) {
//            actionPath = getDependencyNotFromPath(parameter);
        }

        return findActionForNameAndHttpMethod(actionPath, HttpMethod.POST, actions);
    }

    private String getDependencyFromPath(RmdParameter parameter) {
        List<String> uriParts = new ArrayList<String>(Arrays.asList(parameter.getAction().getPath().split("/")));
        int idx = uriParts.indexOf(String.format(PATH_DEPENDENCY_FORMAT, parameter.getName()));
        uriParts = uriParts.subList(0, idx);
        String uri = uriParts.stream().collect(Collectors.joining("/"));
        uri = uri.replace("{parent}", "{id}");
        return uri;
    }

//    private String getDependencyNotFromPath(RmdParameter parameter) {
//        System.out.println("getDependencyPathToOther");
//        Predicate<String> isParameter = item -> item.indexOf("{") != -1;
//        List<String> uriParts = new ArrayList<String>(Arrays.asList(parameter.getAction().getPath().split("/")));
//        uriParts.removeIf(isParameter);
//        uriParts.remove(uriParts.size() - 1);
//        String uri = uriParts.stream().collect(Collectors.joining("/"));
//
//        String entityName = parameter.getName().replaceAll(ID_DEPENDENCIES_UNDERSCORE_ID, EMPTY_STRING) + "s";
//
//        return uri + "/" + entityName;
//    }

    private Optional<RmdAction> findActionForNameAndHttpMethod(String actionPath, HttpMethod httpMethod,
            SortedSet<RmdAction> actions) {
        return actions.stream().filter(a -> actionPath.equals(a.getPath()) && httpMethod == a.getHttpMethod())
                .findFirst();
    }

    private Boolean isDependencyFromPath(RmdParameter parameter) {
        return ParameterContext.PATH == parameter.getContext() && PATH_DEPENDENCY_NAMES.contains(parameter.getName());
    }

//    private Boolean isDependencyToOther(RmdParameter parameter) {
//        return parameter.getName().indexOf(ID_DEPENDENCIES_UNDERSCORE_ID) != -1;
//    }
}