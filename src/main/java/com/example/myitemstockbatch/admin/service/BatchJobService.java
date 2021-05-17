package com.example.myitemstockbatch.admin.service;

import com.example.myitemstockbatch.admin.dto.JobRequest;
import com.example.myitemstockbatch.admin.dto.JobStatusResponse;
import org.quartz.Job;
import org.quartz.JobKey;
import org.springframework.stereotype.Service;


public interface BatchJobService {
    JobStatusResponse getAllBatchJobs();

    boolean launchBatchJob(JobKey jobKey);

    boolean resetBatchJob(JobKey jobKey);

    boolean addBatchJob(JobRequest jobRequest, Class<? extends Job> jobClass);

    boolean deleteBatchJob(JobKey jobKey);

    boolean isJobRunning(JobKey jobKey);

    boolean isJobExists(JobKey jobKey);

    String getJobState(JobKey jobKey);
}
