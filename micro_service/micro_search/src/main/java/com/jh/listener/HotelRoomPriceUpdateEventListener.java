package com.jh.listener;

import com.jh.entity.Room;
import com.jh.entity.RoomPrice;
import com.jh.event.EventListener;
import com.jh.event.constant.EventConstant;
import com.jh.feign.HotelFeign;
import com.jh.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 搜索服务监听酒店客房价格更新事件
 */
@Component
public class HotelRoomPriceUpdateEventListener  implements EventListener<RoomPrice> {

    @Autowired
    private HotelFeign hotelFeign;
    @Autowired
    private ISearchService iSearchService;

    @Override
    public String getEventType() {
        return EventConstant.EVENT_HOTEL_ROOM_PRICE_UPDATE;
    }

    /**
     * 搜索服务对于修改酒店客房价格事件的处理
     * @param roomPrice
     */
    @Override
    public void eventHandler(RoomPrice roomPrice) {

        System.out.println("搜索服务接收到更新酒店客房价格事件："+roomPrice);
        Room room = hotelFeign.selectHotelIdByRoomId(roomPrice.getRid()).getData();
        iSearchService.updateRoomPrice(room.getHid(),roomPrice);
    }
}
