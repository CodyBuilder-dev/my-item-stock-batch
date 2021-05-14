package com.example.myitemstockbatch.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


@Slf4j
//todo: 왜 그냥 Job을 구현하지 않고 QuartzJobBean을 상속할까?
public class CronJob extends QuartzJobBean {
    private int MAX_SLEEP_IN_SECONDS = 5;

    private volatile Thread currThread;

    @Override
    //todo: execute와 executeInternal의 차이는 무엇일까?
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        int jobId = jobDataMap.getInt("jobId");
        JobKey jobKey = context.getJobDetail().getKey();

        currThread = Thread.currentThread();
        log.info("============================================================================");
        log.info("CronJob started :: sleep : {} jobId : {} jobKey : {} - {}", MAX_SLEEP_IN_SECONDS, jobId, jobKey, currThread.getName());

        IntStream.range(0, 10).forEach(i -> {
            log.info("CronJob Counting - {}", i);
            try {
                TimeUnit.SECONDS.sleep(MAX_SLEEP_IN_SECONDS);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        });
        log.info("CronJob ended :: jobKey : {} - {}", jobKey, currThread.getName());
        log.info("============================================================================");
    }
}
