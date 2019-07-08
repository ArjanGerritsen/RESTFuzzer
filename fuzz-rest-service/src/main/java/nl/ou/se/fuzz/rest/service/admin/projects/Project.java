package nl.ou.se.fuzz.rest.service.admin.projects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	@Column(name = "description")
	@NotNull
	private String description;

	@Column(name = "oas_url")
	@NotNull
	private String oasUrl;

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

	public String getOasUrl() {
		return oasUrl;
	}

	public void setOasUrl(String oasUrl) {
		this.oasUrl = oasUrl;
	}
}