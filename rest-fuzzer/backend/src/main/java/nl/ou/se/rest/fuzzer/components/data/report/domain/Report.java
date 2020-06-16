package nl.ou.se.rest.fuzzer.components.data.report.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import nl.ou.se.rest.fuzzer.components.data.fuz.domain.FuzProject;

@Entity(name = "reports")
public class Report {
	
	// variable(s)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String description;
    
    @NotNull
    private FuzProject project;

    private String metaDataTuplesJson;

    private String output;

    // getter(s) and setter(s)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FuzProject getProject() {
		return project;
	}

	public void setProject(FuzProject project) {
		this.project = project;
	}

	public String getMetaDataTuplesJson() {
		return metaDataTuplesJson;
	}

	public void setMetaDataTuplesJson(String metaDataTuplesJson) {
		this.metaDataTuplesJson = metaDataTuplesJson;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
}
