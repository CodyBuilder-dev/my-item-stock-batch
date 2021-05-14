package com.example.myitemstockbatch.config;

import com.example.myitemstockbatch.quartz.service.JobsListener;
import com.example.myitemstockbatch.quartz.service.TriggersListener;
//import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

@Configuration
//@PropertySource("classpath:application.properties")
public class QuartzConfiguration {

    //todo: 커스텀 Lister 쓰는 이유는?
    private TriggersListener triggersListener;

    private JobsListener jobsListener;

    // 이걸 사용하게 되면, spring boot의 autoconfigure를 사용하게 되므로
    // application.properties를 덮어쓴다
//    private QuartzProperties quartzProperties;

    QuartzConfiguration(TriggersListener triggersListener,
                        JobsListener jobsListener
//                        QuartzProperties quartzProperties
    ) {
        this.triggersListener =triggersListener;
        this.jobsListener = jobsListener;
//        this.quartzProperties = quartzProperties;
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

        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        schedulerFactoryBean.setJobFactory(jobFactory);

        schedulerFactoryBean.setApplicationContext(applicationContext);

//        Properties properties = new Properties();
//        properties.putAll(quartzProperties.getProperties());

        schedulerFactoryBean.setGlobalTriggerListeners(triggersListener);
        schedulerFactoryBean.setGlobalJobListeners(jobsListener);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
//        schedulerFactoryBean.setQuartzProperties(properties);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
        return schedulerFactoryBean;
    }
}
