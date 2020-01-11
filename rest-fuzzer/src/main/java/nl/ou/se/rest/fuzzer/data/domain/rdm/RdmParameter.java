package nl.ou.se.rest.fuzzer.data.domain.rdm;

public class RdmParameter {

    // variables
    private int position;
    private String name;
    private boolean required;
    private String description;
    private RdmParameterType type;
    private RdmParameterContext context;

    // constructor
    public RdmParameter(int position, String name, boolean required, String description, String type,
            String context) {
        this.position = position;
        this.name = name;
        this.required = required;
        this.description = description;
        this.type = RdmParameterType.valueOf(type);
        this.context = RdmParameterContext.valueOf(context);
    }

    // getters and setters
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
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
}