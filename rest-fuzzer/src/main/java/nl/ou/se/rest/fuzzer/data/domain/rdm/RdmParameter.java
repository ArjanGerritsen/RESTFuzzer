package nl.ou.se.rest.fuzzer.data.domain.rdm;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RdmParameter implements Comparable<RdmParameter> {

	// variables
	private Integer position;
	private String name;
	private boolean required;
	private String description;
	private RdmParameterType type;
	private RdmParameterContext context;
	private String pattern;
	private String format;

	// constructor
	public RdmParameter(int position, String name, boolean required, String description, String type, String context,
			String pattern, String format) {
		this.position = position;
		this.name = name;
		this.required = required;
		this.description = description;
		this.type = RdmParameterType.valueOf(type);
		this.context = RdmParameterContext.valueOf(context);
		this.pattern = pattern;
		this.format = format;
	}

	// methods
	public int compareTo(RdmParameter other) {
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

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RdmParameterType getType() {
		return type;
	}

	public void setType(RdmParameterType type) {
		this.type = type;
	}

	public RdmParameterContext getContext() {
		return context;
	}

	public void setContext(RdmParameterContext context) {
		this.context = context;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	// toString
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}