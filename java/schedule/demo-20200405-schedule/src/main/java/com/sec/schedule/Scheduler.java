package com.sec.schedule;

import com.sec.schedule.job.Job;

import java.util.Collection;

public interface Scheduler extends Runnable {
    public String getName();

    public Collection<Job> getJobsWaiting();

    public Collection<Job> getJobsRunning();

    public void submit(Job job);

    public Job removeFromWaitingQueue(String jobId);

    public void stop();
}
