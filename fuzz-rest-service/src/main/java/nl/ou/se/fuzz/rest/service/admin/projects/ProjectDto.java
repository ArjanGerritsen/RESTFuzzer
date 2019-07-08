package nl.ou.se.fuzz.rest.service.admin.projects;

public class ProjectDto {

	private Integer id;
	private String status;
	private String description;
	private String oasUrl;
	private Long createdAt;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOasUrl() {
		return oasUrl;
	}
	public void setOasUrl(String oasUrl) {
		this.oasUrl = oasUrl;
	}
	public Long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}	
}