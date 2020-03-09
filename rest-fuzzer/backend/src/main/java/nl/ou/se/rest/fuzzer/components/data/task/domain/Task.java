package nl.ou.se.rest.fuzzer.components.data.task.domain;

import java.time.LocalDateTime;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import nl.ou.se.rest.fuzzer.components.shared.JsonUtil;

@Entity(name = "tasks")
public class Task {

    // variables
    public static final String META_DATA_SUT_ID = "sut_id";
    public static final String META_DATA_PROJECT_ID = "project_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String canonicalName;

    private String metaDataTuplesJson;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime startedAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime crashedAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime finishedAt;

    // constructors
    public Task() {
    }

    public Task(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    // methods
    public Map<String, Object> getMetaDataTuples() {
        return JsonUtil.stringToMap(this.metaDataTuplesJson);
    }

    public void setMetaDataTuples(Map<String, Object> metaDataTuples) {
        this.metaDataTuplesJson = JsonUtil.mapToString(metaDataTuples);
    }

    public boolean isForSut(Long sutId) {
        if (this.getMetaDataTuples().containsKey(META_DATA_SUT_ID)) {
            Integer value = (Integer) this.getMetaDataTuples().get(META_DATA_SUT_ID);
            return value.equals(sutId.intValue());
        }
        return false;
    }

    public boolean isForProject(Long projectId) {
        if (this.getMetaDataTuples().containsKey(META_DATA_PROJECT_ID)) {
            Integer value = (Integer) this.getMetaDataTuples().get(META_DATA_PROJECT_ID);
            return value.equals(projectId.intValue());
        }
        return false;
    }

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

    public String getMetaDataTuplesJson() {
        return metaDataTuplesJson;
    }

    public void setMetaDataTuplesJson(String metaDataTuplesJson) {
        this.metaDataTuplesJson = metaDataTuplesJson;
    }

    // toString
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}