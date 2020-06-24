package com.jh.micro_hotel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.jh")
@EnableEurekaClient
@MapperScan("com.jh.dao")
public class MicroHotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroHotelApplication.class, args);
    }

}
