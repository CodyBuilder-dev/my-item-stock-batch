package com.example.myitemstockbatch.quartz.controller;

import com.example.myitemstockbatch.quartz.dto.ApiResponse;
import com.example.myitemstockbatch.quartz.dto.JobRequest;
import com.example.myitemstockbatch.quartz.dto.JobStatusResponse;
import com.example.myitemstockbatch.quartz.job.CronJob;
import com.example.myitemstockbatch.quartz.job.SimpleJob;
import com.example.myitemstockbatch.quartz.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/quartz/schedule")
public class ScheduleController {
    private ScheduleService scheduleService;

    ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    //todo : Model, ModelMap, ModelAndView 차이 확인하기
    //todo : Model 정확한 주입 방식 다시 확인
    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    public ModelAndView getAllJobs(ModelAndView mv) {
        mv.addObject("allJobs", scheduleService.getAllJobs());
        mv.setViewName("index");
        return mv;
    }

}
