package ar.com.sodhium.commons.concurrent;

/**
 * Class to represent tasks to be executed in order.
 * 
 * @author Roberto G. Fernandez
 *
 */
public interface TaskHandler {
    void execute();
    String getDescription();
}
