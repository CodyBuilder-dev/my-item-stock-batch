package com.example.myitemstockbatch.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class JobStatusResponse {
    private int numOfAllJobs;
    private int numOfGroups;
    private int numOfRunningJobs;
    private List<JobResponse> jobs;
}
