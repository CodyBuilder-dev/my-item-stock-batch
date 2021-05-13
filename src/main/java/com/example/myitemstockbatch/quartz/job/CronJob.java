package com.example.myitemstockbatch.quartz.job;

public class CronJob {
    private int MAX_SLEEP_IN_SECONDS = 5;

    private volatile Thread currThread;

    @Override
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
