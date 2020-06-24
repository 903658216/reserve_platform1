package com.jh.feign;

import com.jh.entity.Hotel;
import com.jh.entity.ResultData;
import com.jh.entity.Room;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("micro-hotel")
public interface HotelFeign {


    @RequestMapping("/hotel/save")
    ResultData<Boolean> insertHotel(@RequestBody Hotel hotel);

    @RequestMapping("/hotel/list")
    ResultData<List<Hotel>> hotelList();

    @RequestMapping("/seleteHotelById")
    ResultData<Hotel> seleteHotelById(@RequestBody Integer id);

    @RequestMapping("/hotel/updateHotelById")
    ResultData<Boolean> updateHotelById(@RequestBody Hotel hotel,@RequestParam Integer ocid);

    @RequestMapping("/hotel/deleteHotelById")
    ResultData<Boolean> deleteHotelById(@RequestParam Integer id);

    @RequestMapping("/hotel/selectRoomByHid")
    ResultData<List<Room>> selectRoomByHid(@RequestParam Integer id);
}
