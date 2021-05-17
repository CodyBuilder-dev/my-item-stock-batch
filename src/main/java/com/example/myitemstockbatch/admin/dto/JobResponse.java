package com.example.myitemstockbatch.admin.dto;

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
