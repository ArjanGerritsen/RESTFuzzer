package nl.ou.se.rest.fuzzer.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

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
import nl.ou.se.rest.fuzzer.data.domain.rdm.RdmAction;
import nl.ou.se.rest.fuzzer.data.domain.rdm.factory.RdmActionFactory;

public class RestModelExtractor {

	// variables
	private String location;
	private List<RdmAction> actions = new ArrayList<>();

	// constructor
	public RestModelExtractor(String location) {
		this.location = location;
	}

	// methods
	public void processV2() {
		Swagger s = new SwaggerParser().read(location);
		s.getPaths().entrySet().forEach(e -> handlePath(s.getHost(), s.getBasePath(), e));
	}

	public void handlePath(String host, String basePath, Entry<String, Path> pathEntry) {
		pathEntry.getValue().getOperationMap().entrySet()
				.forEach(o -> handleOperation(host, basePath, pathEntry.getKey(), o));
	}

	public void handleOperation(String host, String basePath, String key, Entry<HttpMethod, Operation> operationEntry) {
		RdmActionFactory rdmActionFactory = new RdmActionFactory();

		String url = host + basePath + key;
		rdmActionFactory.create(url, operationEntry.getKey().toString());

		// add parameters
		operationEntry.getValue().getParameters().forEach(p -> handleParameter(rdmActionFactory, p));

		// add responses
		operationEntry.getValue().getResponses().entrySet().forEach(r -> handleResponse(rdmActionFactory, r));

		actions.add(rdmActionFactory.build());
	}

	public void handleParameter(RdmActionFactory rdmActionFactory, Parameter parameter) {
		rdmActionFactory.addParameter(parameter.getName(), parameter.getRequired(), parameter.getDescription(),
				parameter.getIn().toUpperCase(), getParameterType(parameter).toUpperCase(), parameter.getPattern(),
				getParameterFormat(parameter));
	}

	private String getParameterType(Parameter parameter) {
		String type = null;

		if (parameter instanceof QueryParameter) {
			QueryParameter qp = (QueryParameter) parameter;
			type = qp.getType();
			// TODO min-max-etc ... 
		} else if (parameter instanceof PathParameter) {
			PathParameter pp = (PathParameter) parameter;
			type = pp.getType();
		} else if (parameter instanceof FormParameter) {
			FormParameter fp = (FormParameter) parameter;
			type = fp.getType();
		} else {
			throw new IllegalAccessError(String.format("Unknown parametertype: ", parameter.getClass().getName()));
		}

		if (type == null) {
			System.out.println(String.format("Type is null for parameter: %s", parameter.getName()));
			return "DATE";
		}

		return type;
	}

	private String getParameterFormat(Parameter parameter) {
		String format = null;

		if (parameter instanceof QueryParameter) {
			QueryParameter qp = (QueryParameter) parameter;
			format = qp.getFormat();
		} else if (parameter instanceof PathParameter) {
			PathParameter pp = (PathParameter) parameter;
			format = pp.getFormat();
		} else if (parameter instanceof FormParameter) {
			FormParameter fp = (FormParameter) parameter;
			format = fp.getFormat();
		}

		return format;
	}

	public void handleResponse(RdmActionFactory rdmActionFactory, Entry<String, Response> responseEntry) {
		rdmActionFactory.addResponse(responseEntry.getKey(), responseEntry.getValue().getDescription());
	}

	// getters and setters
	public List<RdmAction> getRdmActions() {
		return this.actions;
	}

	public static void main(String[] args) {
		// RestModelExtractor rme = new
		// RestModelExtractor("http://localhost/wordpress/rest-api/schema");
		RestModelExtractor rme = new RestModelExtractor(
				"/ws/git/ou-prototype/rest-fuzzer/src/main/resources/schema.json");
		rme.processV2();
		System.out.println(rme.getRdmActions());
	}
}