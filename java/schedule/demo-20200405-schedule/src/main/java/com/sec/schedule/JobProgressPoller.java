package com.sec.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobProgressPoller extends Thread{
    public static final long DEFAULT_INTERVAL_MSEC = 500;
    Logger LOG = LoggerFactory.getLogger(JobProgressPoller.class);
    private Job job;
    private long intervalMs;
    boolean terminate = false;

}
