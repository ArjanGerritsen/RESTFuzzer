package nl.ou.se.rest.fuzzer.data.domain.rdm;

import java.util.SortedSet;
import java.util.TreeSet;

public class RdmAction {

    private String path;
    private RdmHttpMethod method;
    private SortedSet<RdmParameter> parameters = new TreeSet<>();
    private SortedSet<RdmResponse> responses = new TreeSet<>();

    public RdmAction(String path, String httpMethod) {
        this.path = path;
        this.method = RdmHttpMethod.valueOf(httpMethod);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RdmHttpMethod getMethod() {
        return method;
    }

    public void setMethod(RdmHttpMethod method) {
        this.method = method;
    }

    public SortedSet<RdmParameter> getParameters() {
        return parameters;
    }

    public void setParameters(SortedSet<RdmParameter> parameters) {
        this.parameters = parameters;
    }

    public SortedSet<RdmResponse> getResponses() {
        return responses;
    }

    public void setResponses(SortedSet<RdmResponse> responses) {
        this.responses = responses;
    }
}