package com.jh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.dao.HotelMapper;
import com.jh.entity.City;
import com.jh.entity.Hotel;
import com.jh.entity.ResultData;
import com.jh.event.constant.EventConstant;
import com.jh.event.util.EventUtil;
import com.jh.feign.CityFeign;
import com.jh.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class IHotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

    @Autowired
    private HotelMapper hotelmapper;

    @Autowired
    private CityFeign cityFeign;

    @Autowired
    private EventUtil eventUtil;

    /**
     * 新增酒店
     * @param hotel
     * @return
     */
    @Transactional
    @Override
    public boolean save(Hotel hotel) {

        Boolean flag = super.save(hotel);

//        cityFeign.cityUpdateHoterNumberById(hotel.getCid(),1);
        //发布新增酒店的消息
        eventUtil.publishEvent(EventConstant.EVENT_HOTEL_INSERT,hotel);
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

    @Override
    public List<Hotel> list() {
        List<Hotel> hotelList = super.list();
        hotelList.stream().forEach(hotel -> {
            //获得酒店锁对应的城市的id
            Integer cid = hotel.getCid();
            //获得城市对象
            ResultData<City> cityResultData = cityFeign.selectCityById(cid);
            //将城市对象设置到酒店对象中
            hotel.setCity(cityResultData.getData());

        });

        return hotelList;
    }
}
