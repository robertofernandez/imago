package ar.com.sodhium.commons.events;

public interface EventsListener {
    void execute(String remittent, EventParameters parameters);
}
