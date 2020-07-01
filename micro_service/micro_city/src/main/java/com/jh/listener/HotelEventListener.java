package com.jh.listener;


import com.jh.entity.Hotel;
import com.jh.event.EventListener;
import com.jh.event.constant.EventConstant;
import com.jh.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 城市服务对于：酒店事件的监听处理器
 */
@Component
public class HotelEventListener implements EventListener<Hotel> {

    @Autowired
    private ICityService iCityService;

    /**
     * 处理“酒店新增”事件
     * @return
     */
    @Override
    public String getEventType() {

        return EventConstant.EVENT_HOTEL_INSERT;
    }

    /**
     * 实际触发后的处理方法
     * @param msg
     */
    @Override
    public void eventHandler(Hotel msg) {

        System.out.println("城市服务，接收到酒店新增事件，进行了相应的操作"+msg);
        iCityService.updateHotelNumById(msg.getCid(),1);
    }
}
