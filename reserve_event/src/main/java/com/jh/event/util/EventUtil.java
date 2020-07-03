package com.jh.event.util;

import com.jh.event.constant.EventConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 创建队列，发布消息
     * @param eventType
     * @param msg
     */
    public void publishEvent(String eventType,Object msg){
        rabbitTemplate.convertAndSend(EventConstant.EXCHANGE_NAME,eventType,msg);
    }
}
