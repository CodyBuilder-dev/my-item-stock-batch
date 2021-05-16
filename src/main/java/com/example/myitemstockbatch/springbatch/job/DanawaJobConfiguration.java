package com.example.myitemstockbatch.springbatch.job;

import com.example.myitemstockbatch.springbatch.service.DanawaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j // log 사용을 위한 lombok 어노테이션
@RequiredArgsConstructor // 생성자 DI를 위한 lombok 어노테이션
@Configuration
public class DanawaJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory; // 생성자 DI 받음
    private final StepBuilderFactory stepBuilderFactory; // 생성자 DI 받음
    private final DanawaService danawaService;

    @Bean
    public Job minimalPriceJob() {
        return jobBuilderFactory.get("minimalPriceJob")
                .start(saveStep())
                .build();
    }

    @Bean
    public Step saveStep() {
        return stepBuilderFactory.get("saveStep")
                .tasklet((contribution, chunkContext) -> {
                    danawaService.saveDanawaMinPrice(6562283);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}