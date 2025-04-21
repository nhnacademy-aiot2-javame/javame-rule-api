package com.nhnacademy.exam.javameruleapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rule")
public class RuleController {

    @GetMapping
    public String rule(){
        return "rule";
    }
}
