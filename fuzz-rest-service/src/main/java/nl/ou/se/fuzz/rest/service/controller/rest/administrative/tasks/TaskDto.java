package nl.ou.se.fuzz.rest.service.controller.rest.administrative.tasks;

public class TaskDto {

	private Integer id;
	private String description;
	private String type;
	private String status;
	private String payload;
	private Long createdAt;
	private Long lastStartedAt;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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