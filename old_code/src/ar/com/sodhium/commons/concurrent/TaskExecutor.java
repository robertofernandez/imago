package ar.com.sodhium.commons.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ar.com.sodhium.commons.concurrent.status.StatusListener;
import ar.com.sodhium.commons.concurrent.status.TaskExecutorStatus;
import ar.com.sodhium.commons.concurrent.status.TaskExecutorStatusBuilder;
import ar.com.sodhium.commons.exceptions.ExceptionsListener;

/**
 * Class responsible for executing
 * 
 * @author Roberto G. Fernandez
 * 
 */
public class TaskExecutor extends Thread {
    private boolean active;
    private final BlockingQueue<TaskHandler> tasks;
    private ExceptionsListener exceptionsListener;
    private StatusListener statusListener;

    public TaskExecutor(final String name, ExceptionsListener exceptionsListener) {
        this.exceptionsListener = exceptionsListener;
        active = true;
        tasks = new LinkedBlockingQueue<TaskHandler>();
        setName("_te_" + name);
    }

    public void init() {
        setActive(true);
        start();
    }

    public void dispose() {
        setActive(false);
        interrupt();
    }

    public void pushTask(final TaskHandler task) {
        try {
            tasks.put(task);
            sendQueueUpdatedMessage("task added");
        } catch (final InterruptedException e) {
            exceptionsListener.onException(e);
            return;
        }
    }

    private void sendQueueUpdatedMessage(String operation) {
        if (statusListener != null) {
            TaskExecutorStatus taskExecutorStatus = TaskExecutorStatusBuilder.getBuilder(operation)
                    .withParameter("elements amount", Integer.valueOf(tasks.size())).create();
            statusListener.onStatusChange(taskExecutorStatus);
        }
    }

    private void sendTaskTakenMessage(String description) {
        if (statusListener != null) {
            TaskExecutorStatus taskExecutorStatus = TaskExecutorStatusBuilder.getBuilder("task taken")
                    .withParameter("current task", description)
                    .withParameter("elements amount", Integer.valueOf(tasks.size())).create();
            statusListener.onStatusChange(taskExecutorStatus);
        }
    }

    private void sendTaskExecutedMessage(Long elapsedTime) {
        if (statusListener != null) {
            TaskExecutorStatus taskExecutorStatus = TaskExecutorStatusBuilder.getBuilder("task executed")
                    .withParameter("elements amount", Integer.valueOf(tasks.size()))
                    .withParameter("elapsed time", elapsedTime).create();
            statusListener.onStatusChange(taskExecutorStatus);
        }
    }

    private void sendTaskErrorMessage() {
        if (statusListener != null) {
            TaskExecutorStatus taskExecutorStatus = TaskExecutorStatusBuilder.getBuilder("task ERROR")
                    .withParameter("elements amount", Integer.valueOf(tasks.size())).create();
            statusListener.onStatusChange(taskExecutorStatus);
        }
    }

    private void sendStatusMessage(String status) {
        if (statusListener != null) {
            TaskExecutorStatus taskExecutorStatus = TaskExecutorStatusBuilder.getBuilder("task status update")
                    .withParameter("elements amount", Integer.valueOf(tasks.size())).withParameter("status", status)
                    .create();
            statusListener.onStatusChange(taskExecutorStatus);
        }
    }

    @Override
    public void run() {
        while (!Thread.interrupted() && active) {
            try {
                TaskHandler task = tasks.take();
                sendTaskTakenMessage(task.getDescription());
                Long startTime = System.currentTimeMillis();
                sendStatusMessage("executing");
                task.execute();
                sendStatusMessage("execution finished");
                Long endTime = System.currentTimeMillis();
                sendTaskExecutedMessage(endTime - startTime);
            } catch (final InterruptedException e) {
                exceptionsListener.onException(e);
                return;
            } catch (final Exception e) {
                try {
                    sendTaskErrorMessage();
                    exceptionsListener.onException(e);
                } catch (Exception e2) {
                    continue;
                }
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public Integer getQueueLevel() {
        if (tasks != null) {
            return tasks.size();
        } else {
            return 0;
        }
    }

    public void setStatusListener(StatusListener statusListener) {
        this.statusListener = statusListener;
    }

    @Override
    public String toString() {
        return "TaskExecutor [active=" + active + ", tasks=" + tasks + ", exceptionsListener=" + exceptionsListener
                + ", statusListener=" + statusListener + "]";
    }
}