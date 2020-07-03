package com.jh.event.config;

import com.jh.event.constant.EventConstant;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventPublishConfiguration {


    /**
     * 事件发布者需要创建
     * 事件接受者也需要创建
     *
     * 创建了一个交换机
     * @return
     */
    @Bean
    public DirectExchange getExchange(){

        return new DirectExchange(EventConstant.EXCHANGE_NAME,true,false);
    }
}
