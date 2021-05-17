package com.example.myitemstockbatch.admin.controller;

import com.example.myitemstockbatch.admin.dto.ApiResponse;
import com.example.myitemstockbatch.admin.dto.JobRequest;
import com.example.myitemstockbatch.admin.service.BatchJobService;
import com.example.myitemstockbatch.quartz.job.BatchJob;
import com.example.myitemstockbatch.quartz.job.CronJob;
import com.example.myitemstockbatch.quartz.job.SimpleJob;
import org.quartz.JobKey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
public class BatchRestController {
    private BatchJobService batchJobService;

    BatchRestController(BatchJobService batchJobService) {
        this.batchJobService = batchJobService;
    }

    @RequestMapping(value="/job/run", method= RequestMethod.POST)
    public ResponseEntity<?> launchBatchJob(@ModelAttribute JobRequest jobRequest) {
        if (jobRequest.getJobName() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Require jobName"),
                    HttpStatus.BAD_REQUEST);
        }
        JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
        if (batchJobService.isJobExists(jobKey)) {
            if (!batchJobService.isJobRunning(jobKey)) {
                batchJobService.launchBatchJob(jobKey);
            } else {
                return new ResponseEntity<>(new ApiResponse(false, "Job already in running state"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Job does not exits"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Job launched successfully"), HttpStatus.OK);

    }

    @RequestMapping(value="/job/reset", method= RequestMethod.POST)
    public ResponseEntity<?> resetBatchJob(@ModelAttribute JobRequest jobRequest) {
        if (jobRequest.getJobName() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Require jobName"),
                    HttpStatus.BAD_REQUEST);
        }
        JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
        if (batchJobService.isJobExists(jobKey)) {
            if (!batchJobService.isJobRunning(jobKey)) {
                batchJobService.resetBatchJob(jobKey);
            } else {
                return new ResponseEntity<>(new ApiResponse(false, "Job already in running state"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Job does not exits"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Job launched successfully"), HttpStatus.OK);

    }


    @RequestMapping(value="/job", method= RequestMethod.POST)
    public ResponseEntity<?> addBatchJob(@ModelAttribute JobRequest jobRequest) {
        if (jobRequest.getJobName() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Require jobName"),
                    HttpStatus.BAD_REQUEST);
        }

        JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
        if (!batchJobService.isJobExists(jobKey)) {
            batchJobService.addBatchJob(jobRequest, BatchJob.class);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Job already exits"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Job created successfully"), HttpStatus.CREATED);

    }

    @RequestMapping(value="/job", method= RequestMethod.DELETE)
    public ResponseEntity<?> deleteBatchJob(@ModelAttribute JobRequest jobRequest) {
        JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
        if (batchJobService.isJobExists(jobKey)) {
            if (!batchJobService.isJobRunning(jobKey)) {
                batchJobService.deleteBatchJob(jobKey);
            } else {
                return new ResponseEntity<>(new ApiResponse(false, "Job already in running state"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Job does not exits"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Job deleted successfully"), HttpStatus.OK);
    }

}
