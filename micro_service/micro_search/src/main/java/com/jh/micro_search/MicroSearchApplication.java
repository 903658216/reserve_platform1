package com.jh.micro_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.jh")
@EnableEurekaClient
public class MicroSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroSearchApplication.class, args);
    }

}
