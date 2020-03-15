package nl.ou.se.rest.fuzzer.components.data.fuz.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity(name = "fuz_dictionaries")
public class FuzDictionary {
	

	// variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String name;

    @NotNull
    @NotEmpty
    private String itemsCsv;

    // constructors
    public FuzDictionary(String name, String itemsCsv) {
    	this.name = name;
    	this.itemsCsv = itemsCsv;
    }

    // methods
    
    
    // getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItemsCsv() {
		return itemsCsv;
	}

	public void setItemsCsv(String itemsCsv) {
		this.itemsCsv = itemsCsv;
	}
    
	// toString
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}