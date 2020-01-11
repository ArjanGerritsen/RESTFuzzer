package nl.ou.se.fuzz.rest.service.settings.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "settings")
public class SettingJpa implements Comparable<SettingJpa> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotEmpty
	@Size(min = 3, max = 32)
	private String key;
	
	@NotNull
	@NotEmpty
	@Size(max = 512)
	private String description;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private SettingType type;

	@NotNull
	@Enumerated(EnumType.STRING)
	private SettingScope scope;
	
	@NotNull
	@NotEmpty
	@Size(min = 1, max = 32)	
	private String value;

	@NotNull
	@Column(name = "created_at")
	private Date createdAt;
	
	@NotNull
	@Column(name = "updated_at")
	private Date updatedAt;

	@Override
	public int compareTo(SettingJpa other) {
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}