package nl.ou.se.rest.fuzzer;

public abstract class Constants {

    public static final String INFO_TASK_SCHEDULAR_START = "Starting %s task(s)";
    public static final String INFO_TASK_SCHEDULAR_START_TASK = "Task %s with id %s started";
    public static final String WARN_TASK_SCHEDULAR_TASK_NOT_STARTED = "Task %s with id %s NOT started";

    public static final String ERROR_TASK_EXECUTION_FACTORY_UNKNOWN = "Unkown execution type %s in task table in database";
    
    public static final String WARN_TASK_SUT_NOT_FOUND = "Task %s: Sut with id %s not found";
    public static final String WARN_TASK_VALUE_FOR_KEY_NOT_Present = "Task %s: no value for key %s";

    public static final String INFO_TASK_START = "Task %s start";
    public static final String INFO_TASK_STOP = "Task %s stop";

	public static final String ENTITY_GRAPH_RMD_SUTS_ALL_RELATIONS = "rmd.suts.all_relations";
    public static final String ENTITY_GRAPH_FUZ_PROJECTS_ALL_RELATIONS = "fuz.projects.all_relations";
    
}