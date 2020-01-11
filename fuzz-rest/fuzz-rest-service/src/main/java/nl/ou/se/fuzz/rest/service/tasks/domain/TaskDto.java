package nl.ou.se.fuzz.rest.service.tasks.domain;

public class TaskDto implements Comparable<TaskDto> {

	private Integer id;
	private String description;
	private TaskType type;
	private TaskStatus status;
	private String payload;
	private Long createdAt;
	private Long lastStartedAt;

	@Override
	public int compareTo(TaskDto other) {
		return this.getCreatedAt().compareTo(other.getCreatedAt());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getLastStartedAt() {
		return lastStartedAt;
	}

	public void setLastStartedAt(Long lastStartedAt) {
		this.lastStartedAt = lastStartedAt;
	}
}