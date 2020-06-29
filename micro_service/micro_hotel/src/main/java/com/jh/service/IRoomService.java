package com.jh.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jh.entity.Room;

import java.util.List;

public interface IRoomService extends IService<Room> {

    List<Room> selectRoomListByHid(Integer hid);
}
