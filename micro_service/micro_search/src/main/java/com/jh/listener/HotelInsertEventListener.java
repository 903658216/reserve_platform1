package com.jh.listener;

import com.jh.entity.City;
import com.jh.entity.Hotel;
import com.jh.entity.ResultData;
import com.jh.event.EventListener;
import com.jh.event.constant.EventConstant;
import com.jh.feign.CityFeign;
import com.jh.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 搜索服务监听新增酒店事件，处理类
 */
@Component
public class HotelInsertEventListener implements EventListener<Hotel> {

    @Autowired
    private ISearchService iSearchService;

    @Autowired
    private CityFeign cityFeign;

    @Override
    public String getEventType() {
        return EventConstant.EVENT_HOTEL_INSERT;
    }

    /**
     * 将新增的酒店信息同步到索引库中
     * @param hotel
     */
    @Override
    public void eventHandler(Hotel hotel) {
        System.out.println("搜索服务接收到新增酒店的事件："+hotel);

        //通过酒店信息搜索对应的城市对象
//        City city = cityFeign.selectCityById(hotel.getCid()).getData();
        ResultData<City> cityData = cityFeign.selectCityById(hotel.getCid());

        hotel.setCity(cityData.getData());

        iSearchService.insertDoc(hotel);
    }
}
