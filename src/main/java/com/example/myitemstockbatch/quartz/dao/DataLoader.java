package com.example.myitemstockbatch.quartz.dao;

import com.example.myitemstockbatch.quartz.dto.JobRequest;
import com.example.myitemstockbatch.quartz.job.CronJob;
import com.example.myitemstockbatch.quartz.job.SimpleJob;
import com.example.myitemstockbatch.quartz.service.ScheduleService;
import org.quartz.JobDataMap;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.time.LocalDateTime;

//todo: DataLoader의 역할과 실행시점은 언제일까?
//todo: 왜 ApplicationListener를 구현할까?
//todo: 이미 Controller에서 CRUD를 하는데 여기서 왜 또 addJob을 수행할까?
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private ScheduleService scheduleService;

    DataLoader(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //simple job 생성
        JobRequest jobRequest = new JobRequest();
        jobRequest.setJobName("simpleJob");
        jobRequest.setStartDateAt(LocalDateTime.now());
        jobRequest.setRepeatCount(50);
        jobRequest.setRepeatIntervalInSeconds(30);
        scheduleService.addJob(jobRequest, SimpleJob.class);

        //cron job 생성
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobId", "123456789");
        jobRequest = new JobRequest();
        jobRequest.setJobName("cronJob1");
        jobRequest.setCronExpression("0 * * ? * *"); //every min
        jobRequest.setJobDataMap(jobDataMap);
        scheduleService.addJob(jobRequest, CronJob.class);

//        jobRequest = new JobRequest();
//        jobRequest.setJobName("cronJob2");
//        jobRequest.setCronExpression("0 */5 * ? * *"); //every 5 min
//        scheduleService.addJob(jobRequest, CronJob2.class);

    }
}