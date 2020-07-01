package com.jh.event.listener;

import com.jh.event.EventListener;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnBean(EventListener.class)
public class RabbitMQListener {

    @Autowired
    private List<EventListener> eventListeners;

    /**
     * 监听指定队列
     * @param message
     */
    @RabbitListener(queues = "${spring.application.name}-queue")
    public  void  msgHandler(Message message){

        //获取发布消息的路由键- 事件类型
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        //交给对应的EventListener处理
        eventListeners.stream().forEach(event->{
            //判断事件的类型和路由键是否匹配
            if (event.getEventType().equals(routingKey)){
                //匹配上之后，直接调用当前的event处理方法处理该消息
                byte[] msgBytes = message.getBody();
                event.eventHandler(SerializationUtils.deserialize(msgBytes));

            }

        });

    }



}
