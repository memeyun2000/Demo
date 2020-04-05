package com.sec.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.sec.schedule.Job.Status;

public class FIFOScheduler implements Scheduler {
    List<Job> queue = new LinkedList<Job>();
    private ExecutorService executor;
    private SchedulerListener listener;
    boolean terminate = false;
    Job runningJob = null;
    private String name;


    public static Logger LOG = LoggerFactory.getLogger(FIFOScheduler.class);


    public FIFOScheduler(String name, ExecutorService executor, SchedulerListener listener) {
        this.name = name;
        this.executor = executor;
        this.listener = listener;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection getJobsWaiting() {
        List<Job> list = new LinkedList<Job>();
        synchronized (queue) {
            for (Job job : queue) {
                list.add(job);
            }
        }
        return list;
    }

    @Override
    public Collection getJobsRunning() {
        List<Job> list = new LinkedList<Job>();
        if (runningJob != null) {
            list.add(runningJob);
        }
        return list;
    }

    @Override
    public void submit(Job job) {
        job.setStatus(Job.Status.PENDING);
        synchronized (queue) {
            queue.add(job);
            queue.notify();
        }
    }

    @Override
    public Job removeFromWaitingQueue(String jobId) {
        return null;
    }

    @Override
    public void stop() {
        terminate = true;
        synchronized (queue) {
            queue.notify();
        }

    }

    @Override
    public void run() {
        synchronized (queue) {
            while (terminate == false) {
                if (runningJob != null || queue.isEmpty() == true) {
                    try {
                        queue.wait(500);
                    } catch (Exception e) {
                        LOG.error("Exception in queue wait...");
                    }
                    continue;
                }

                runningJob = queue.remove(0);

                final Scheduler scheduler = this;
                this.executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (runningJob.isAborted()) {
                            runningJob.setStatus(Status.ABORT);
                            runningJob.aborted = false;
                            synchronized (queue) {
                                queue.notify();
                            }
                            return;
                        }

                        runningJob.setStatus(Status.RUNNING);
                        if (listener != null) {
                            listener.jobStarted(scheduler, runningJob);
                        }
                        runningJob.run();
                        if (runningJob.isAborted()) {
                            runningJob.setStatus(Status.ABORT);
                        } else {
                            if (runningJob.getException() != null) {
                                runningJob.setStatus(Status.ERROR);
                            } else {
                                runningJob.setStatus(Status.FINISHED);
                            }
                        }
                        if (listener != null) {
                            listener.jobFinished(scheduler, runningJob);
                        }
                        // reset aborted flag to allow retry
                        runningJob.aborted = false;
                        runningJob = null;
                        synchronized (queue) {
                            queue.notify();
                        }
                    }
                });
            }

        }


    }
}
