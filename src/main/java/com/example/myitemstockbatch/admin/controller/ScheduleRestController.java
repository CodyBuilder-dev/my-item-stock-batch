package com.example.myitemstockbatch.admin.controller;

import com.example.myitemstockbatch.admin.dto.ApiResponse;
import com.example.myitemstockbatch.admin.dto.JobRequest;
import com.example.myitemstockbatch.quartz.job.CronJob;
import com.example.myitemstockbatch.quartz.job.SimpleJob;
import com.example.myitemstockbatch.admin.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/quartz/schedule")
public class ScheduleRestController {
    private ScheduleService scheduleService;

    ScheduleRestController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @RequestMapping(value = "/job", method=RequestMethod.GET)
    public ResponseEntity<?> showTestText(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //todo: @ModelAttribute / @RequestBody중 어떤거 써야 하는지 알아내기
    @RequestMapping(value = "/job", method = RequestMethod.POST)
    public ResponseEntity<?> addScheduleJob(@ModelAttribute JobRequest jobRequest) {
        log.debug("add schedule job :: jobRequest : {}", jobRequest);
        if (jobRequest.getJobName() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Require jobName"),
                    HttpStatus.BAD_REQUEST);
        }

        JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
        if (!scheduleService.isJobExists(jobKey)) {
            if (jobRequest.getCronExpression() == null) {
                scheduleService.addJob(jobRequest, SimpleJob.class);
            } else {
                scheduleService.addJob(jobRequest, CronJob.class);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Job already exits"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Job created successfully"), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/job", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteScheduleJob(@ModelAttribute JobRequest jobRequest) {
        JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
        if (scheduleService.isJobExists(jobKey)) {
            if (!scheduleService.isJobRunning(jobKey)) {
                scheduleService.deleteJob(jobKey);
            } else {
                return new ResponseEntity<>(new ApiResponse(false, "Job already in running state"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Job does not exits"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Job deleted successfully"), HttpStatus.OK);
    }


    @RequestMapping(value = "/job/pause", method = RequestMethod.PUT)
    public ResponseEntity<?> pauseJob(@ModelAttribute JobRequest jobRequest) {
        JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
        if (scheduleService.isJobExists(jobKey)) {
            if (!scheduleService.isJobRunning(jobKey)) {
                scheduleService.pauseJob(jobKey);
            } else {
                return new ResponseEntity<>(new ApiResponse(false, "Job already in running state"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Job does not exits"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Job paused successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/job/resume", method = RequestMethod.PUT)
    public ResponseEntity<?> resumeJob(@ModelAttribute JobRequest jobRequest) {
        JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
        if (scheduleService.isJobExists(jobKey)) {
            String jobState = scheduleService.getJobState(jobKey);

            if (jobState.equals("PAUSED")) {
                scheduleService.resumeJob(jobKey);
            } else {
                return new ResponseEntity<>(new ApiResponse(false, "Job is not in paused state"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Job does not exits"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Job resumed successfully"), HttpStatus.OK);
    }
}

