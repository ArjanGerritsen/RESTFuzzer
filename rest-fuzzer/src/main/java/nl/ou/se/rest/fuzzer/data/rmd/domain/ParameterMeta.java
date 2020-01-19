package nl.ou.se.rest.fuzzer.data.rmd.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity(name = "rmd_parametermetas")
public class ParameterMeta {

    // constants
    public static final String PATTERN = "PATTERN";
    public static final String FORMAT = "FORMAT";

    public static final String MIN_VALUE = "MIN_VALUE";
    public static final String MAX_VALUE = "MAX_VALUE";
    
    public static final String MIN_LENGTH = "MIN_LENGTH";
    public static final String MAX_LENGTH = "MAX_LENGTH";
    
    public static final String MIN_ITEMS = "MIN_ITEMS";
    public static final String MAX_ITEMS = "MAX_ITEMS";

    public static final String ENUM = "ENUM";
    public static final String DEFAULT = "DEFAULT";

    // variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
    private Long id;

	@NotNull
	@NotEmpty
    private String key;

	@NotNull
	@NotEmpty
	@Lob
	private Object value;

    // constructors
    public ParameterMeta(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    // getters and setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    // toString
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }    
}