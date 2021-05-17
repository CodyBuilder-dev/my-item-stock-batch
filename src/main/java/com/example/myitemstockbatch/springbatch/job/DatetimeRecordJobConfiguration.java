package com.example.myitemstockbatch.springbatch.job;

import com.example.myitemstockbatch.springbatch.service.DatetimeRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.TaskletStepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DatetimeRecordJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DatetimeRecordService datetimeRecordService;

    //todo : name을 명시하지 않고 get만 쓰면 No bean named "DatetimeRecordBatchJob" 발생하는데 왜그럴까
    @Bean(name="DatetimeRecordBatchJob")
    public Job dateTimeRecordJob(){
        return jobBuilderFactory.get("DatetimeRecordBatchJob")
                .start(dateTimeRecordStep1(null))
                .build();
    }

    @Bean
    @JobScope
    public Step dateTimeRecordStep1(@Value("#{jobParameters[date]}")String date) {
        return stepBuilderFactory.get("DatetimeRecordStep1")
                .tasklet((contribution, chunkContext) -> {
                    datetimeRecordService.recordDatetimeNow();
//                    log.info(date.toString() + "까지 넣었는데 왜 안되냐");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}