package com.jh.event;

/**
 * 事件监听接口
 */
public interface EventListener<T> {

    /**
     * 监听的事件类型
     * @return
     */
    String getEventType();


    /**
     * 事件处理方法 ：核心技术
     * @param msg
     */
    void eventHandler(T msg);

}
