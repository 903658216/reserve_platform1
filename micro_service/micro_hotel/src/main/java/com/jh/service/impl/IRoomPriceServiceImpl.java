package com.jh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.dao.RoomMapper;
import com.jh.dao.RoomPriceMapper;
import com.jh.entity.RoomPrice;
import com.jh.service.IRoomPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IRoomPriceServiceImpl extends ServiceImpl<RoomPriceMapper, RoomPrice> implements IRoomPriceService {

    @Autowired
    private RoomPriceMapper roomPriceMapper;

    /**
     * 根据房间编号查询客房价格列表
     * @param rid
     * @return
     */
    @Override
    public List<RoomPrice> selectRoomPriceListByRid(Integer rid) {

        return roomPriceMapper.selectRoomPriceListByRid(rid);
    }
}
