package nl.ou.se.rest.fuzzer.data.task.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity(name = "tasks")
public class Task {

	// variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	private String canonicalName;

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
}