package nl.ou.se.fuzz.rest.service.shared.domain;

public class EventDto implements Comparable<EventDto> {

	private Integer id;
	private EventType type;
	private String content;
	private Long createdAt;

	@Override
	public int compareTo(EventDto other) {
		return this.getCreatedAt().compareTo(other.getCreatedAt());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
}