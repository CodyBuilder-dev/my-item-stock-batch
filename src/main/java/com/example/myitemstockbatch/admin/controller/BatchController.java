package com.example.myitemstockbatch.admin.controller;

import com.example.myitemstockbatch.admin.service.BatchJobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/batch")
public class BatchController {

    private BatchJobService batchJobService;

    BatchController(BatchJobService batchJobService) {
        this.batchJobService = batchJobService;
    }

    @GetMapping("/jobs")
    public ModelAndView showAllBatchJobs(ModelAndView mv){
        mv.addObject("allJobs", batchJobService.getAllBatchJobs());
        mv.setViewName("batch");
        return mv;
    }
}
