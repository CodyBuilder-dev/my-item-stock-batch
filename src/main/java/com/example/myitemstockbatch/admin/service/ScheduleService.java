package com.example.myitemstockbatch.admin.service;

import com.example.myitemstockbatch.admin.dto.JobRequest;
import com.example.myitemstockbatch.admin.dto.JobStatusResponse;
import org.quartz.Job;
import org.quartz.JobKey;

public interface ScheduleService {
    JobStatusResponse getAllJobs();

    boolean addJob(JobRequest jobRequest, Class<? extends Job> jobClass);

    boolean deleteJob(JobKey jobKey);

    boolean pauseJob(JobKey jobKey);

    boolean resumeJob(JobKey jobKey);


    boolean isJobRunning(JobKey jobKey);

    boolean isJobExists(JobKey jobKey);

    String getJobState(JobKey jobKey);

}
