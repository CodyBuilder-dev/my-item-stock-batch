package com.example.myitemstockbatch;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping("")
    public String mainPage() {
        return "redirect:quartz/schedule/jobs";
    }
}
