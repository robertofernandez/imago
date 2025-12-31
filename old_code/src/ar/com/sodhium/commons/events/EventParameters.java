package ar.com.sodhium.commons.events;

import java.util.concurrent.ConcurrentHashMap;

public class EventParameters {

    /**
     * It is concurrent so can be passed around adding elements
     */
    protected ConcurrentHashMap<String, Object> parameters;

    public EventParameters() {
        parameters = new ConcurrentHashMap<>();
    }

    public void setParameter(String name, Object value) {
        parameters.put(name, value);
    }

    public Object getParameter(String name) {
        return parameters.get(name);
    }

    public String getStringParameter(String name) {
        if(!parameters.containsKey(name)) {
            return null;
        }
        return parameters.get(name).toString();
    }

    public String getStringParameter(String name, String defaultResult) {
        if(!parameters.containsKey(name)) {
            return defaultResult;
        }
        return parameters.get(name).toString();
    }

    public boolean containsParameter(String name) {
        return parameters.containsKey(name);
    }
    
    public ConcurrentHashMap<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "EventParameters [" + parameters + "]";
    }

}
