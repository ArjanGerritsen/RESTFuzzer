package nl.ou.se.rest.fuzzer.service.task;

import java.time.LocalDateTime;

public class TaskDto {

    // variables
    private Long id;
    private String canonicalName;
    private String metaDataTuplesJson;
    private LocalDateTime startedAt;
    private LocalDateTime crashedAt;
    private LocalDateTime finishedAt;

    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCanonicalName() {
        return canonicalName;
    }
    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }
    public String getMetaDataTuplesJson() {
        return metaDataTuplesJson;
    }
    public void setMetaDataTuplesJson(String metaDataTuplesJson) {
        this.metaDataTuplesJson = metaDataTuplesJson;
    }
    public LocalDateTime getStartedAt() {
        return startedAt;
    }
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
    public LocalDateTime getCrashedAt() {
        return crashedAt;
    }
    public void setCrashedAt(LocalDateTime crashedAt) {
        this.crashedAt = crashedAt;
    }
    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }
    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }
}