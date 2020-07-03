package com.jh.listener;

import com.jh.entity.Room;
import com.jh.event.EventListener;
import com.jh.event.constant.EventConstant;
import com.jh.feign.HotelFeign;
import com.jh.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HotelRoomInsertEventListener implements EventListener<Room> {

    @Autowired
    private ISearchService iSearchService;

    @Autowired
    private HotelFeign hotelFeign;

    @Override
    public String getEventType() {
        return EventConstant.EVENT_HOTEL_ROOM_INSERT;
    }

    /**
     * 将新增的客房信息同步到索引库中
     * @param room
     */
    @Override
    public void eventHandler(Room room) {

        System.out.println("搜索服务接收到新增酒店客房事件："+room);
        iSearchService.insertRoom(room);
        iSearchService.insertRoomPrice(room.getHid(),room.getRoomPriceList());

    }
}
