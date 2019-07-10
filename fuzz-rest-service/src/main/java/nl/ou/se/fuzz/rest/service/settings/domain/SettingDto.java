package nl.ou.se.fuzz.rest.service.settings.domain;

public class SettingDto implements Comparable<SettingDto> {

	private Long id;
	private String key;
	private String description;
	private SettingType type;
	private SettingScope scope;
	private String value;
	private Long createdAt;
	private Long updatedAt;

	@Override
	public int compareTo(SettingDto other) {
		int scope = this.getScope().compareTo(other.getScope());
		return (scope == 0) ? this.getKey().compareTo(other.getKey()) : scope;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public SettingType getType() {
		return type;
	}
	public void setType(SettingType type) {
		this.type = type;
	}
	public SettingScope getScope() {
		return scope;
	}
	public void setScope(SettingScope scope) {
		this.scope = scope;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
	public Long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
}