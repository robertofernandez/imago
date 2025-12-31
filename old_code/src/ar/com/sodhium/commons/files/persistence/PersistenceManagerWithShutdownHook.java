package ar.com.sodhium.commons.files.persistence;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import ar.com.sodhium.commons.concurrent.ShutdownHook;
import ar.com.sodhium.commons.concurrent.TaskExecutor;
import ar.com.sodhium.commons.concurrent.TaskHandler;
import ar.com.sodhium.commons.exceptions.ExceptionsListener;

public class PersistenceManagerWithShutdownHook<T> extends PersistenceManager<T> {
    private AtomicBoolean shutdown;
    private TaskExecutor executor;
    private ShutdownHook shutdownHook;
    private ExceptionsListener exceptionsListener;

    public PersistenceManagerWithShutdownHook(ShutdownHook shutdownHook, String name,
            ExceptionsListener exceptionsListener) {
        this.shutdownHook = shutdownHook;
        this.exceptionsListener = exceptionsListener;
        shutdown = new AtomicBoolean(false);
        executor = new TaskExecutor("PM_" + name, exceptionsListener);
        executor.init();
    }

    @Override
    public void writeFile(T element, String filePath) throws IOException {
        executor.pushTask(new TaskHandler() {
            @Override
            public String getDescription() {
                return "Write file " + filePath;
            }

            @Override
            public void execute() {
                try {
                    if (!shutdown.get()) {
                        performWriting(element, filePath);
                    }
                } catch (Exception e) {
                    exceptionsListener.onException(e);
                }
            }
        });
    }

    private void performWriting(T element, String filePath) throws IOException {
        super.writeFile(element, filePath);
    }

    public void attemptShutdown() {
        shutdown.set(true);
        executor.pushTask(new TaskHandler() {
            @Override
            public String getDescription() {
                return "send shutdown message";
            }

            @Override
            public void execute() {
                shutdownHook.isClearToShutDown();
            }
        });
    }
}
