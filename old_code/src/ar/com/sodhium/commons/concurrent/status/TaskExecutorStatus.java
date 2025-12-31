package ar.com.sodhium.commons.concurrent.status;

import java.util.HashMap;

public class TaskExecutorStatus {
    private String operation;
    private HashMap<String, Object> properties;

    public TaskExecutorStatus(String operation, HashMap<String, Object> properties) {
        this.operation = operation;
        this.properties = properties;
    }

    public String getOperation() {
        return operation;
    }

    public HashMap<String, Object> getProperties() {
        return properties;
    }

    public Object getProperty(String name) {
        return properties.get(name);
    }
}
