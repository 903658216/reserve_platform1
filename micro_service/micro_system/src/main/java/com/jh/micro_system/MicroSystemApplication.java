package com.jh.micro_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.jh")
@EnableEurekaClient
public class MicroSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroSystemApplication.class, args);
    }

}
