package com.jh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.dao.RoomMapper;
import com.jh.entity.Room;
import com.jh.entity.RoomPrice;
import com.jh.service.IRoomPriceService;
import com.jh.service.IRoomService;
import com.jh.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class IRoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private IRoomPriceService iRoomPriceService;

    @Transactional
    @Override
    public boolean save(Room room) {

        Boolean flag = super.save(room);
        List<RoomPrice> roomPrices =new ArrayList<>();

        //同时生成最近一个月的价格列表
        for (int i =0; i<30;i++){
            RoomPrice roomPrice = new RoomPrice()
                    .setRid(room.getId())
                    .setType(0)//非普通房价
                    .setPrice(room.getDefaultPrice())
                    .setNumber(0)
                    .setHasNumber(room.getNumber())
                    .setDate(DateUtil.getNextDate(i));
            roomPrices.add(roomPrice);
        }

        iRoomPriceService.saveBatch(roomPrices);
        return flag;
    }

    /**
     * 根据酒店编号查询酒店客房类型列表
     * @param hid
     * @return
     */
    @Override
    public List<Room> selectRoomListByHid(Integer hid) {

        List<Room> roomList = roomMapper.selectRoomListByHid(hid);
        return roomList;
    }
}
