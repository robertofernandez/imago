package ar.com.sodhium.commons.concurrent;

public interface ShutdownHook {

    /**
     * Indicates system can shutdown with no resources under risk.
     */
    void isClearToShutDown();
}
