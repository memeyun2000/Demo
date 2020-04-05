package com.sec.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

public abstract class Job {


    public static enum Status {
        READY,
        PENDING,
        RUNNING,
        FINISHED,
        ERROR,
        ABORT;

        public boolean isReady() {
            return this == READY;
        }

        public boolean isRunning() {
            return this == RUNNING;
        }

        public boolean isPending() {
            return this == PENDING;
        }

    }


    private String jobName;
    String id;
    Object result;
    Date dateCreated;
    Date dateStarted;
    Date dateFinished;
    Status status;

    public static Logger LOG = LoggerFactory.getLogger(Job.class);

    transient boolean aborted = false;

    String errorMessage;

    private transient Throwable exception;

    private transient JobListener listener;

    private long progressUpdateIntervalMs;


    public Job(String jobId , String jobName, JobListener listener, long progressUpdateIntervalMs) {
        this.jobName = jobName;
        this.listener = listener;
        this.progressUpdateIntervalMs = progressUpdateIntervalMs;

        dateCreated = new Date();
        id = jobId;

        setStatus(Status.READY);
    }

    public Job(String jobName,JobListener listener) {
        this(jobName,listener,JobProgressPoller.DEFAULT_INTERVAL_MSEC);
    }

    public Job(String jobName , JobListener listener ,long progressUpdateIntervalMs) {
        this.jobName = jobName;
        this.listener = listener;
        this.progressUpdateIntervalMs = progressUpdateIntervalMs;

        dateCreated = new Date();
        id = dateCreated + "_" + super.hashCode();

        setStatus(Status.READY);
    }


    public void setStatus(Status status) {
        if (this.status == status) {
            return;
        }
        Status before = this.status;
        Status after = status;

        if (listener != null) {
            listener.beforeStatusChange(this, before, after);
        }
        this.status = status;
        if (listener != null) {
            listener.afterStatusChange(this, before, after);
        }
    }


    public String getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public JobListener getListener() {
        return listener;
    }

    public void setListener(JobListener listener) {
        this.listener = listener;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == hashCode();
    }


    public boolean isTerminate() {
        return !this.getStatus().isReady() && !this.getStatus().isPending() && !this.getStatus().isRunning();
    }

    public boolean isRunning() {
        return this.getStatus().isRunning();
    }


    public void run() {
        //TODO:
        JobProgressPoller progressPoller = new JobProgressPoller();
        try {
            dateStarted = new Date();
            result = runJob();
            this.exception = null;
            this.errorMessage = null;
            dateFinished = new Date();

        } catch (Exception e) {
            LOG.error("Job fail!!!");
            this.exception = e;
            this.errorMessage = e.getMessage();
            result = e.getMessage();

        } catch (Throwable e) {
            LOG.error("Job fail!!!");
            this.exception = e;
            this.errorMessage = e.getMessage();
            result = e.getMessage();

        } finally {

        }
    }

    public Object getReturn() {
        return this.result;
    }


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    protected abstract Object runJob() throws Throwable;

    public abstract int progress();

    public abstract Map<String,Object> info();

    protected abstract boolean jobAbort();

    public void abort() {
        aborted = this.jobAbort();
    }

    public boolean isAborted() {
        return aborted;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public Date getDateFinished() {
        return dateFinished;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Throwable getException() {
        return exception;
    }
}
