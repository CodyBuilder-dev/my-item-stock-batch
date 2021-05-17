package com.example.myitemstockbatch.quartz.job;

import com.example.myitemstockbatch.springbatch.job.DatetimeRecordJobConfiguration;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.repository.support.SimpleJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Slf4j
public class BatchJob extends QuartzJobBean {
    //todo : 다른 모든 생성자 방식은 안되고 오직 Autowired만 되는데 그 이유는?
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private ApplicationContext ctx;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("date", LocalDateTime.now().toString())
                    .toJobParameters();
            try {
                jobLauncher.run(ctx.getBean("DatetimeRecordBatchJob",Job.class), jobParameters);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
    }

}
