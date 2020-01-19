package nl.ou.se.rest.fuzzer.data.rmd.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity(name = "rmd_parameters")
public class Parameter implements Comparable<Parameter> {

    // variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	
	@NotNull
	@NotEmpty
    private Integer position;

	@NotNull
	@NotEmpty
	@Size(min = 1, max = 64)
	private String name;

	@NotNull
	@NotEmpty
	private Boolean required;

	@NotNull
	@NotEmpty
    private String description;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ParameterType type;
	
	@NotNull
	@Enumerated(EnumType.STRING)
    private ParameterContext context;
	
	@OneToMany
    private List<ParameterMeta> metas = new ArrayList<>();

    // constructor
    public Parameter(int position, String name, Boolean required, String description, String type, String context) {
        this.position = position;
        this.name = name;
        this.required = required;
        this.description = description;
        this.type = ParameterType.valueOf(type);
        this.context = ParameterContext.valueOf(context);
    }

    // methods
    public int compareTo(Parameter other) {
        return this.getPosition().compareTo(other.getPosition());
    }

    // getters and setters
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ParameterType getType() {
        return type;
    }

    public void setType(ParameterType type) {
        this.type = type;
    }

    public ParameterContext getContext() {
        return context;
    }

    public void setContext(ParameterContext context) {
        this.context = context;
    }

    public List<ParameterMeta> getMetas() {
        return metas;
    }

    public void setMetas(List<ParameterMeta> metas) {
        this.metas = metas;
    }

    // toString
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}