package com.jh.micro_city;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.jh")
@EnableEurekaClient
@MapperScan("com.jh.dao")
@EnableTransactionManagement
public class MicroCityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroCityApplication.class, args);
    }

}
