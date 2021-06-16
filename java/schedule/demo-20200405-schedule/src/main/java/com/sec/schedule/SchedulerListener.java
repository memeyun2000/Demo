package com.sec.schedule;

import com.sec.schedule.job.Job;

public interface SchedulerListener {
    public void jobStarted(Scheduler scheduler, Job job);
    public void jobFinished(Scheduler scheduler, Job job);
}
