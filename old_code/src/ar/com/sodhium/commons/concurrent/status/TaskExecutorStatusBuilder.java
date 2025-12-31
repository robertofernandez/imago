package ar.com.sodhium.commons.concurrent.status;

import java.util.HashMap;

public class TaskExecutorStatusBuilder {
    private HashMap<String, Object> parameters;
    private String operation;

    public static TaskExecutorStatusBuilder getBuilder(String operation) {
        return new TaskExecutorStatusBuilder(operation);
    }
    
    private TaskExecutorStatusBuilder(String operation) {
        this.operation = operation;
        parameters = new HashMap<>();
    }
    
    public TaskExecutorStatusBuilder withParameter(String name, Object value) {
        parameters.put(name, value);
        return this;
    }
    
    public TaskExecutorStatus create() {
        return new TaskExecutorStatus(operation, parameters);
    }
}
