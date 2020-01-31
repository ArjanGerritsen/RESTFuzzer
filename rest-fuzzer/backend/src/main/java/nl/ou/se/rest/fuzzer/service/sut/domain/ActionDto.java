package nl.ou.se.rest.fuzzer.service.sut.domain;

import java.util.ArrayList;
import java.util.List;

public class ActionDto {

    // variables
    private Long id;
    private String path;
    private String httpMethod;
    private List<ParameterDto> parameters = new ArrayList<>();
    private List<ResponseDto> responses = new ArrayList<>();

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public List<ParameterDto> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterDto> parameters) {
        this.parameters = parameters;
    }

    public List<ResponseDto> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseDto> responses) {
        this.responses = responses;
    }
}
