package com.example.myitemstockbatch.quartz.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JobResponse {
    private String jobName;
    private String groupName;
    private String jobStatus;
    private String scheduleTime;
    private String lastFiredTime;
    private String nextFireTime;
}
