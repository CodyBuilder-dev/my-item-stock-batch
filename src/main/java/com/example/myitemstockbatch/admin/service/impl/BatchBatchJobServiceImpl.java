package com.example.myitemstockbatch.admin.service.impl;

import com.example.myitemstockbatch.admin.dto.JobRequest;
import com.example.myitemstockbatch.admin.dto.JobResponse;
import com.example.myitemstockbatch.admin.dto.JobStatusResponse;
import com.example.myitemstockbatch.admin.service.BatchJobService;
import com.example.myitemstockbatch.quartz.utils.DateTimeUtils;
import com.example.myitemstockbatch.quartz.utils.JobUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BatchBatchJobServiceImpl implements BatchJobService {
    private SchedulerFactoryBean schedulerFactoryBean;
    private ApplicationContext context;

    BatchBatchJobServiceImpl(SchedulerFactoryBean schedulerFactoryBean,
                        ApplicationContext context) {
        this.schedulerFactoryBean = schedulerFactoryBean;
        this.context = context;
    }

    @Override
    public JobStatusResponse getAllBatchJobs() {
        JobResponse jobResponse;
        JobStatusResponse jobStatusResponse = new JobStatusResponse();
        List<JobResponse> jobs = new ArrayList<>();
        int numOfRunningJobs = 0;
        int numOfGroups = 0;
        int numOfAllJobs = 0;

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            for (String groupName : scheduler.getJobGroupNames()) {
                if ("BATCH".equals(groupName)) { // BATCH 그룹일때만 세기
                    numOfGroups++;
                    for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);

                        jobResponse = JobResponse.builder()
                                .jobName(jobKey.getName())
                                .groupName(jobKey.getGroup())
                                .scheduleTime(DateTimeUtils.toString(triggers.get(0).getStartTime()))
                                .lastFiredTime(DateTimeUtils.toString(triggers.get(0).getPreviousFireTime()))
                                .nextFireTime(DateTimeUtils.toString(triggers.get(0).getNextFireTime()))
                                .build();

                        if (isJobRunning(jobKey)) {
                            jobResponse.setJobStatus("RUNNING");
                            numOfRunningJobs++;
                        } else {
                            String jobState = getJobState(jobKey);
                            jobResponse.setJobStatus(jobState);
                        }
                        numOfAllJobs++;
                        jobs.add(jobResponse);
                    }
                }

            }
        } catch (SchedulerException e) {
            log.error("[schedulerdebug] error while fetching all job info", e);
        }

        jobStatusResponse.setNumOfAllJobs(numOfAllJobs);
        jobStatusResponse.setNumOfRunningJobs(numOfRunningJobs);
        jobStatusResponse.setNumOfGroups(numOfGroups);
        jobStatusResponse.setJobs(jobs);
        return jobStatusResponse;
    }

    @Override
    public boolean launchBatchJob(JobKey jobKey) {
        return false;
    }

    @Override
    public boolean resetBatchJob(JobKey jobKey) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        try {
            List<? extends Trigger>  triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                TriggerKey triggerKey = trigger.getKey();
                if (scheduler.getTriggerState(triggerKey).equals(Trigger.TriggerState.ERROR)) {
                    scheduler.resetTriggerFromErrorState(triggerKey);
                }
            }

            return true;
        } catch (SchedulerException e) {
            log.error("[schedulerdebug] error occurred while deleting job with jobKey : {}", jobKey, e);
        }
        return false;
    }

    @Override
    public boolean addBatchJob(JobRequest jobRequest, Class<? extends Job> jobClass) {

        JobKey jobKey = null;
        JobDetail jobDetail;
        Trigger trigger;

        try {
            trigger = JobUtils.createTrigger(jobRequest);
            jobDetail = JobUtils.createJob(jobRequest, jobClass, context);
            jobKey = JobKey.jobKey(jobRequest.getJobName(), jobRequest.getJobGroup());

            Date dt = schedulerFactoryBean.getScheduler().scheduleJob(jobDetail, trigger);
            log.debug("Batch Job with jobKey : {} scheduled successfully at date : {}", jobDetail.getKey(), dt);
            return true;
        } catch (SchedulerException e) {
            log.error("error occurred while scheduling with jobKey : {}", jobKey, e);
        }
        return false;
    }

    @Override
    public boolean deleteBatchJob(JobKey jobKey) {
        log.debug("[schedulerdebug] deleting job with jobKey : {}", jobKey);
        try {
            return schedulerFactoryBean.getScheduler().deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error("[schedulerdebug] error occurred while deleting job with jobKey : {}", jobKey, e);
        }
        return false;
    }

    @Override
    public boolean isJobRunning(JobKey jobKey) {
        try {
            List<JobExecutionContext> currentJobs = schedulerFactoryBean.getScheduler().getCurrentlyExecutingJobs();
            if (currentJobs != null) {
                for (JobExecutionContext jobCtx : currentJobs) {
                    if (jobKey.getName().equals(jobCtx.getJobDetail().getKey().getName())) {
                        return true;
                    }
                }
            }
        } catch (SchedulerException e) {
            log.error("[schedulerdebug] error occurred while checking job with jobKey : {}", jobKey, e);
        }
        return false;
    }

    @Override
    public boolean isJobExists(JobKey jobKey) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            if (scheduler.checkExists(jobKey)) {
                return true;
            }
        } catch (SchedulerException e) {
            log.error("[schedulerdebug] error occurred while checking job exists :: jobKey : {}", jobKey, e);
        }
        return false;
    }

    @Override
    public String getJobState(JobKey jobKey) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());

            if (triggers != null && triggers.size() > 0) {
                for (Trigger trigger : triggers) {
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    if (Trigger.TriggerState.NORMAL.equals(triggerState)) {
                        return "SCHEDULED";
                    }
                    return triggerState.name().toUpperCase();
                }
            }
        } catch (SchedulerException e) {
            log.error("[schedulerdebug] Error occurred while getting job state with jobKey : {}", jobKey, e);
        }
        return null;
    }
}
