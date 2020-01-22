package nl.ou.se.rest.fuzzer.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import io.swagger.parser.SwaggerParser;
import nl.ou.se.rest.fuzzer.data.rmd.domain.Action;
import nl.ou.se.rest.fuzzer.data.rmd.domain.Sut;
import nl.ou.se.rest.fuzzer.data.rmd.factory.ActionFactory;
import nl.ou.se.rest.fuzzer.data.rmd.factory.ParameterFactory;
import nl.ou.se.rest.fuzzer.data.rmd.factory.ResponseFactory;

public class Extractor {

    // variables
    private Sut sut;

    private String title;
    private String description;
    private String host;
    private String basePath;
    private List<Action> actions = new ArrayList<>();

    private ActionFactory actionFactory = new ActionFactory();
    private ParameterFactory parameterFactory = new ParameterFactory();
    private ResponseFactory responseFactory = new ResponseFactory();

    // constructor
    public Extractor(Sut sut) {
        this.sut = sut;
    }

    // methods
    public void processV2() {
        Swagger s = new SwaggerParser().read(this.sut.getLocation());

        this.title = s.getInfo().getTitle();
        this.description = s.getInfo().getDescription();
        this.host = s.getHost();
        this.basePath = s.getBasePath();

        s.getPaths().entrySet().forEach(e -> processPath(e));
    }

    private void processPath(Entry<String, Path> path) {
        path.getValue().getOperationMap().entrySet().forEach(o -> processOperation(path.getKey(), o));
    }

    private void processOperation(String key, Entry<HttpMethod, Operation> operation) {
        actionFactory.create(key, operation.getKey().toString());

        // add parameters
        operation.getValue().getParameters().forEach(p -> processParameter(p));

        // add responses
        operation.getValue().getResponses().entrySet().forEach(r -> processResponse(r));

        actions.add(actionFactory.build());
    }

    private void processParameter(Parameter parameter) {
        Map<String, Object> values = ExtractorUtil.getTypeAndMetas(parameter);

        String type = values.remove(ExtractorUtil.KEY_TYPE).toString();

        parameterFactory.create(parameter.getName(), parameter.getRequired(), parameter.getDescription(), type,
                parameter.getIn());

        parameterFactory.setMetaDataTuples(values);

        actionFactory.addParameter(parameterFactory.build());
    }

    public void processResponse(Entry<String, Response> responseEntry) {
        actionFactory.addResponse(responseFactory
                .create(Integer.valueOf(responseEntry.getKey()), responseEntry.getValue().getDescription()).build());
    }

    // getters and setters
    public List<Action> getActions() {
        return this.actions;
    }

    public String getHost() {
        return host;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}