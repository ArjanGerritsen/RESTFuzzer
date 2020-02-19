package nl.ou.se.rest.fuzzer.service.fuz.domain;

import java.time.LocalDateTime;

public class FuzResponseDto {

    // variables
    private Long id;
    private int statusCode;
    private String statusDescription;
    private String body;
    private String failureReason;
    private FuzRequestDto request;
    private LocalDateTime recievedAt;

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public FuzRequestDto getRequest() {
        return request;
    }

    public void setRequest(FuzRequestDto request) {
        this.request = request;
    }

    public LocalDateTime getRecievedAt() {
        return recievedAt;
    }

    public void setRecievedAt(LocalDateTime recievedAt) {
        this.recievedAt = recievedAt;
    }
}
