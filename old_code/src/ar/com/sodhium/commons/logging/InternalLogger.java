package ar.com.sodhium.commons.logging;

public interface InternalLogger {
    void log(String category, String message);

    void log(String category, String message, Throwable error);
}
