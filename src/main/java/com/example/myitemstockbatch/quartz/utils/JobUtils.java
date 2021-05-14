package com.example.myitemstockbatch.quartz.utils;

import com.example.myitemstockbatch.quartz.dto.JobRequest;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.quartz.CronExpression.isValidExpression;

public class JobUtils {

    //todo : ApplicationContext를 인자로 받는 이유가 무엇일까?
    public static JobDetail createJob(JobRequest jobRequest, Class<? extends Job> jobClass, ApplicationContext context) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(false); // todo: setDurability가 무슨 의미일까?
        factoryBean.setApplicationContext(context);
        factoryBean.setName(jobRequest.getJobName());
        factoryBean.setGroup(jobRequest.getJobGroup());

        if (jobRequest.getJobDataMap() != null) {
            factoryBean.setJobDataMap(jobRequest.getJobDataMap());
        }

        factoryBean.afterPropertiesSet(); // todo: afterPropertiesSet을 호출하는 이유가 무엇일까?
        return factoryBean.getObject();
    }

    public static Trigger createTrigger(JobRequest jobRequest) {
        String cronExpression = jobRequest.getCronExpression();
        LocalDateTime startDateAt = jobRequest.getStartDateAt();

        if (StringUtils.hasLength(cronExpression)) {
            if (!isValidExpression(cronExpression)) {
                throw new IllegalArgumentException("Provided expression " + cronExpression + " is not a valid cron expression");
            }
            return createCronTrigger(jobRequest);
        } else if (startDateAt != null) {
            return createSimpleTrigger(jobRequest);
        }
        throw new IllegalStateException("unsupported trigger descriptor");
    }

    //todo : CronTrigger와 SimpleTrigger의 차이는 무엇일까?
    private static Trigger createCronTrigger(JobRequest jobRequest) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setName(jobRequest.getJobName());
        factoryBean.setGroup(jobRequest.getJobGroup());
        factoryBean.setCronExpression(jobRequest.getCronExpression());
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW); // todo: setMisfireInstruction은 무엇일까
        try {
            factoryBean.afterPropertiesSet();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return factoryBean.getObject();
    }

    private static Trigger createSimpleTrigger(JobRequest jobRequest) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setName(jobRequest.getJobName());
        factoryBean.setGroup(jobRequest.getJobGroup());
        factoryBean.setStartTime(Date.from(jobRequest.getStartDateAt().atZone(ZoneId.systemDefault()).toInstant()));
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        factoryBean.setRepeatInterval(jobRequest.getRepeatIntervalInSeconds() * 1000); //ms 단위임
        factoryBean.setRepeatCount(jobRequest.getRepeatCount());

        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }
}
