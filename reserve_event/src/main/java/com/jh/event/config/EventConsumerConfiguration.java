package com.jh.event.config;

import com.jh.event.EventListener;
import com.jh.event.util.SpringContextUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnBean(EventListener.class)
public class EventConsumerConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private SpringContextUtil springContextUtil;

    @Autowired
    private List<EventListener> eventListeners;

    @Bean
    public Queue getQueue(){

        //这里的队列的名称是微服务的名称加上-queue
        return new Queue(applicationName+"-queue",true,false,false);
    }


    /**
     * 队列和交换机的绑定
     * @param getQueue
     * @param getExchange
     * @return
     */
    @Bean
    public Binding  getBinding(Queue getQueue, DirectExchange getExchange){

        //循环所有的EventListener
        eventListeners.forEach(event->{
            //获得当前的处理器需要处理的事件类型 - 路由键
            String eventType = event.getEventType();
            System.out.println("绑定的路由键的名称为"+eventType);
            Binding binding = BindingBuilder.bind(getQueue).to(getExchange).with(eventType);

            //动态将绑定对象binding注册到spring容器中
            springContextUtil.registryBean(eventType+event.hashCode(),binding);

        });

        return null;
    }
}


