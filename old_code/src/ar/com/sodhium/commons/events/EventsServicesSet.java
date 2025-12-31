package ar.com.sodhium.commons.events;

import java.util.HashMap;

public class EventsServicesSet {
    private HashMap<String, EventsService> services;

    public EventsServicesSet() {
        services = new HashMap<>();
    }

    public void addService(String name) {
        services.put(name, new EventsService());
    }

    public EventsService getService(String name) {
        if (!services.containsKey(name)) {
            addService(name);
        }
        return services.get(name);
    }
}
