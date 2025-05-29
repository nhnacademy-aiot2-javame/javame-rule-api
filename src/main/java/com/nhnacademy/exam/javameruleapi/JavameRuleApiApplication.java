package com.nhnacademy.exam.javameruleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.nhnacademy.exam.javameruleapi")
@EnableJpaRepositories(basePackages = "com.nhnacademy.exam.javameruleapi")
public class JavameRuleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavameRuleApiApplication.class, args);
    }

}
