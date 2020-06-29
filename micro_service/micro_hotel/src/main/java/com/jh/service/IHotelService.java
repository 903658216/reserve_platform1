package com.jh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jh.entity.Hotel;


public interface IHotelService extends IService<Hotel> {


    boolean updateById1(Hotel hotel, Integer ocid);

    boolean removeById(Integer id);


}
