package com.sec.schedule;

public interface JobListener {
    public void onProgressUpdate(Job job , int progress);

    public void beforeStatusChange(Job job,Job.Status before,Job.Status after);

    public void afterStatusChange(Job job,Job.Status before,Job.Status after);
}
