package com.example.myitemstockbatch.config;

import com.example.myitemstockbatch.quartz.dto.JobRequest;
import com.example.myitemstockbatch.quartz.job.CronJob;
import com.example.myitemstockbatch.quartz.job.SimpleJob;
import com.example.myitemstockbatch.quartz.service.ScheduleService;
import org.quartz.JobDataMap;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.time.LocalDateTime;

//todo: DataLoader의 정확한 용도와 실행 시점 알아내기
//todo: ApplicationListener와 ContextRefreshedEvent 용도 알아내기
//todo: Controller에 이미 addJob 호출부가 있는데 왜 여기서 또 호출하는지 알아내기
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