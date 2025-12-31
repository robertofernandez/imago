package ar.com.sodhium.commons.concurrent.status;

public interface StatusListener {
    void onStatusChange(TaskExecutorStatus taskExecutorStatus);
}
