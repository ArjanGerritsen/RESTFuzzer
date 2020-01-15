package nl.ou.se.rest.fuzzer.rdm.extractor;

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
import nl.ou.se.rest.fuzzer.rdm.domain.RdmAction;
import nl.ou.se.rest.fuzzer.rdm.factory.RdmActionFactory;
import nl.ou.se.rest.fuzzer.rdm.factory.RdmParameterFactory;
import nl.ou.se.rest.fuzzer.rdm.factory.RdmParameterMetaFactory;

public class RdmExtractor {

    // variables
    private String location;
    private List<RdmAction> actions = new ArrayList<>();

    private RdmActionFactory rdmActionFactory = new RdmActionFactory();
    private RdmParameterFactory rdmParameterFactory = new RdmParameterFactory();
    private RdmParameterMetaFactory rdmParameterMetaFactory = new RdmParameterMetaFactory();

    // constructor
    public RdmExtractor(String location) {
        this.location = location;
    }

    // methods
    public void processV2() {
        Swagger s = new SwaggerParser().read(location);
        
        // s.getHost(), s.getBasePath(), TODO dit ergens opslaan ...
        
        s.getPaths().entrySet().forEach(e -> handlePath(e));
    }

    public void handlePath(Entry<String, Path> pathEntry) {
        pathEntry.getValue().getOperationMap().entrySet()
                .forEach(o -> handleOperation(pathEntry.getKey(), o));
    }

    public void handleOperation(String key, Entry<HttpMethod, Operation> operationEntry) {
        rdmActionFactory.create(key, operationEntry.getKey().toString());

        // add parameters
        operationEntry.getValue().getParameters().forEach(p -> handleParameter(p));

        // add responses
        operationEntry.getValue().getResponses().entrySet().forEach(r -> handleResponse(r));

        actions.add(rdmActionFactory.build());
    }

    public void handleParameter(Parameter parameter) {
        Map<String, Object> values = RdmExtractorUtil.getTypeAndMetas(parameter);

        String type = values.remove(RdmExtractorUtil.KEY_TYPE).toString();

        rdmParameterFactory.create(parameter.getName(), parameter.getRequired(), parameter.getDescription(),
                type, parameter.getIn());

        values.entrySet().forEach(entry -> rdmParameterFactory.addMeta(rdmParameterMetaFactory.create(entry.getKey(), entry.getValue()).build()));

        rdmActionFactory.addParameter(rdmParameterFactory.build());
    }

    public void handleResponse(Entry<String, Response> responseEntry) {
        rdmActionFactory.addResponse(responseEntry.getKey(), responseEntry.getValue().getDescription());
    }

    // getters and setters
    public List<RdmAction> getRdmActions() {
        return this.actions;
    }

    public static void main(String[] args) {
        // RestModelExtractor rme = new
        // RestModelExtractor("http://localhost/wordpress/rest-api/schema");
        RdmExtractor extractor = new RdmExtractor("/ws/git/ou-prototype/rest-fuzzer/src/main/resources/schema.json");
        extractor.processV2();
        System.out.println(extractor.getRdmActions());
    }
}