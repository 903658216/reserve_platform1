package com.jh.event.constant;

/**
 * 交换机和路由键常量设置接口
 */
public interface EventConstant {

    /**
     * 交换机的名称
     */
    String EXCHANGE_NAME ="event-exchange";

    /**
     * 酒店新增事件的路由键
     */
    String EVENT_HOTEL_INSERT = "hotel_insert";

    /**
     * 酒店客房类型新增事件的路由键
     */
    String EVENT_HOTEL_ROOM_INSERT ="hotel_room_insert";

    /**
     * 酒店客房价格的修改事件
     */
    String EVENT_HOTEL_ROOM_PRICE_UPDATE ="hotel_room_price_update";

}
