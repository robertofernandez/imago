package ar.com.sodhium.commons.events;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class EventsService {
    private ConcurrentHashMap<String, ArrayList<EventsListener>> listeners;
    private ConcurrentHashMap<String, LinkedBlockingQueue<EventsListener>> volatileListeners;

    public EventsService() {
        listeners = new ConcurrentHashMap<>();
        volatileListeners = new ConcurrentHashMap<>();
    }

    public void register(String name, EventsListener listener) {
        if (!listeners.containsKey(name)) {
            listeners.put(name, new ArrayList<>());
        }
        listeners.get(name).add(listener);
    }

    public void volatileRegister(String name, EventsListener listener) throws InterruptedException {
        if (!volatileListeners.containsKey(name)) {
            volatileListeners.put(name, new LinkedBlockingQueue<>());
        }
        volatileListeners.get(name).put(listener);
    }

    public void trigger(String event, EventParameters parameters, String remittent) {
        if (listeners.containsKey(event)) {
            for (EventsListener listener : listeners.get(event)) {
                listener.execute(remittent, parameters);
            }
        }
        if (volatileListeners.containsKey(event)) {
            LinkedBlockingQueue<EventsListener> listeners = volatileListeners.get(event);
            while (!listeners.isEmpty()) {
                EventsListener listener = listeners.poll();
                if (listener != null) {
                    listener.execute(remittent, parameters);
                }
            }
        }
    }
}
