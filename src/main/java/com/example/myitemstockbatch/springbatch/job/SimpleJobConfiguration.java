package com.example.myitemstockbatch.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j // log 사용을 위한 lombok 어노테이션
@RequiredArgsConstructor // 생성자 DI를 위한 lombok 어노테이션
@Configuration
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory; // 생성자 DI 받음
    private final StepBuilderFactory stepBuilderFactory; // 생성자 DI 받음

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("BatchSimpleJob")
                .start(simpleStep1())
                .start(simpleStep2("유야호"))
                .build();
    }

    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("BatchSimpleStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<");
                    log.info(">>>>> This is BatchSimpleStep1 <<<<<<");
                    log.info(">>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step simpleStep2(@Value("#{jobParameters[name]}")String name) {
        return stepBuilderFactory.get("BatchSimpleStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<");
                    log.info(String.format(">>>>> My Name is %s<<<<<<",name));
                    log.info(">>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}