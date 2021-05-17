package com.example.myitemstockbatch.quartz.config;

import com.example.myitemstockbatch.quartz.listener.JobsListener;
import com.example.myitemstockbatch.quartz.listener.TriggersListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

@Slf4j
@Configuration
public class QuartzConfiguration {

    //todo: 커스텀 Lister 쓰는 이유는?
    //todo: QuartzProperties의 정확한 용도 이해
    private TriggersListener triggersListener;

    private JobsListener jobsListener;

    private QuartzProperties quartzProperties;

    QuartzConfiguration(TriggersListener triggersListener,
                        JobsListener jobsListener,
                        QuartzProperties quartzProperties
    ) {
        this.triggersListener =triggersListener;
        this.jobsListener = jobsListener;
        this.quartzProperties = quartzProperties;
    }

    /**
     * Quartz 관련 설정
     *
     * @param applicationContext the applicationContext
     * @return SchedulerFactoryBean
     */
    //todo: 왜 Applicationcontext를 인자로 받을까?
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        // Application Context 지정
        schedulerFactoryBean.setApplicationContext(applicationContext);

        // JobFactory 지정
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        schedulerFactoryBean.setJobFactory(jobFactory);

        // TriggerListeners, JobListeners 지정
        schedulerFactoryBean.setGlobalTriggerListeners(triggersListener);
        schedulerFactoryBean.setGlobalJobListeners(jobsListener);

        // application.properties 설정 등록
        Properties properties = new Properties();
        properties.putAll(quartzProperties.getProperties());
        properties.put("org.quartz.dataSource.mariadb.URL",System.getenv("SPRING_DATASOURCE_URL"));
        properties.put("org.quartz.dataSource.mariadb.driver",System.getenv("SPRING_DATASOURCE_DRIVER"));
        properties.put("org.quartz.dataSource.mariadb.user",System.getenv("SPRING_DATASOURCE_USERNAME"));
        properties.put("org.quartz.dataSource.mariadb.password",System.getenv("SPRING_DATASOURCE_PASSWORD"));
        schedulerFactoryBean.setQuartzProperties(properties);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);


        return schedulerFactoryBean;
    }
}
