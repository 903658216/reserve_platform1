package com.jh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jh.entity.RoomPrice;

import java.util.List;

public interface RoomPriceMapper extends BaseMapper<RoomPrice> {

    List<RoomPrice> selectRoomPriceListByRid(Integer rid);
}
