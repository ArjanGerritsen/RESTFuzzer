package nl.ou.se.rest.fuzzer.extractor;

import java.util.Map;
import java.util.Map.Entry;

import io.swagger.models.ExternalDocs;
import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import io.swagger.parser.SwaggerParser;
import nl.ou.se.rest.fuzzer.data.domain.rdm.factory.RdmActionFactory;

public class RestModelExtractor {

    private String location;

    public RestModelExtractor(String location) {
        this.location = location;
    }

    public void execute() {
        Swagger swagger = new SwaggerParser().read(location);

        System.out.println(swagger.getHost());
        System.out.println(swagger.getBasePath());

        Map<String, Path> paths = swagger.getPaths();
        paths.entrySet().forEach(e -> handlePath(e));
    }

    public void handlePath(Entry<String, Path> pathEntry) {
        System.out.println("===========================");
        System.out.println(pathEntry.getKey());

        System.out.println("\n" + "Operations:");
        pathEntry.getValue().getOperationMap().entrySet().forEach(o -> handleOperation(o));
    }

    public void handleOperation(Entry<HttpMethod, Operation> operationEntry) {
        RdmActionFactory rdmActionFactory = new RdmActionFactory();

        rdmActionFactory.create("TODO", operationEntry.getKey().toString());

        System.out.println("\n\t" + "Method:");
        System.out.println("\t\t" + operationEntry.getKey());

        System.out.println("\t" + "Parameters:");
        operationEntry.getValue().getParameters().forEach(p -> handleParameter(p));

        System.out.println("\t" + "Responses:");
        operationEntry.getValue().getResponses().entrySet().forEach(r -> handleResponse(r));

        System.out.println("\t" + "Produces:");
        operationEntry.getValue().getProduces().forEach(p -> handleProduces(p));

        System.out.println("\t" + "Consumes:");
        operationEntry.getValue().getConsumes().forEach(p -> handleProduces(p));

        System.out.println("\t" + "Externaldocs:");
        handleExternDocs(operationEntry.getValue().getExternalDocs());
        
        System.out.println(operationEntry.getValue().getSecurity());
    }

    private void handleExternDocs(ExternalDocs ed) {
        if (ed != null) {
            System.out.println(ed.getUrl());
        } else {
            System.out.println("\t\t-");
        }
    }
    
    private void handleProduces(String producing) {
        System.out.println("\t\t" + producing);
    }

    public void handleParameter(Parameter parameter) {
        StringBuffer sb = new StringBuffer();

        sb.append("\t\t");
        sb.append(parameter.getName());
        sb.append(" (required: ");
        sb.append(parameter.getRequired());
        sb.append(" / in: ");
        sb.append(parameter.getIn());
        sb.append(" / description: ");
        sb.append(parameter.getDescription());

        if (parameter instanceof QueryParameter) {
            QueryParameter qp = (QueryParameter) parameter;
            sb.append(" / type: ");
            sb.append(qp.getType());
        }

        if (parameter instanceof PathParameter) {
            PathParameter pp = (PathParameter) parameter;
            sb.append(" / type: ");
            sb.append(pp.getType());
        }

        if (parameter instanceof FormParameter) {
            FormParameter fp = (FormParameter) parameter;
            sb.append(" / type: ");
            sb.append(fp.getType());
        }

//        minimum: 1

        sb.append(")");
        
        System.out.println(sb);
    }

    public void handleResponse(Entry<String, Response> responseEntry) {
        System.out.println("\t\t" + responseEntry.getKey() + ": "  + responseEntry.getValue().getDescription());
    }
    
    
    public static void main(String[] args) {
        RestModelExtractor rme = new RestModelExtractor("http://localhost/wordpress/rest-api/schema");
        rme.execute();
    }
}