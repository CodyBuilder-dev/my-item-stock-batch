package com.example.myitemstockbatch.quartz.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/quartz/scheduler")
public class ScheduleController {
    private ScheduleService scheduleService;

}
