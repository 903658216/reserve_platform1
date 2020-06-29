package com.jh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.dao.HotelMapper;
import com.jh.entity.Hotel;
import com.jh.feign.CityFeign;
import com.jh.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class IHotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

    @Autowired
    private HotelMapper hotelmapper;

    @Autowired
    private CityFeign cityFeign;

    @Transactional
    @Override
    public boolean save(Hotel hotel) {

        Boolean flag = super.save(hotel);
        cityFeign.cityUpdateHoterNumberById(hotel.getCid(),1);
        return flag;

    }


    @Transactional
    @Override
    public  boolean updateById1(Hotel hotel,Integer ocid){

        int flag = hotelmapper.updateById(hotel);
        cityFeign.cityUpdateHoterNumberById(ocid,-1);
        cityFeign.cityUpdateHoterNumberById(hotel.getCid(),1);

        return flag >0;

    }

    @Override
    public boolean removeById(Integer id){

        Hotel hotel = hotelmapper.selectById(id);
        cityFeign.cityUpdateHoterNumberById(hotel.getCid(),-1);
        boolean flag = super.removeById(id);
        return flag;
    }




}
