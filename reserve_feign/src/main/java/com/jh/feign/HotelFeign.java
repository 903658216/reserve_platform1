package com.jh.feign;

import com.jh.entity.Hotel;
import com.jh.entity.ResultData;
import com.jh.entity.Room;
import com.jh.entity.RoomPrice;
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

    @RequestMapping("/hotel/selectHotelById")
    ResultData<Hotel> selectHotelById(@RequestBody Integer id);

    @RequestMapping("/hotel/updateHotelById")
    ResultData<Boolean> updateHotelById(@RequestBody Hotel hotel,@RequestParam Integer ocid);

    @RequestMapping("/hotel/deleteHotelById")
    ResultData<Boolean> deleteHotelById(@RequestParam Integer id);

    @RequestMapping("/room/selectRoomListByHid")
    ResultData<List<Room>> selectRoomListByHid(@RequestParam Integer hid);

    @RequestMapping("/room/insertHotelRoom")
    ResultData<Boolean> insertHotelRoom(@RequestBody  Room room);

    @RequestMapping("/room/selectRoomPriceListByRid")
    ResultData<List<RoomPrice>> selectRoomPriceListByRid(@RequestBody Integer rid);

    @RequestMapping("/room/selectRoomPrice")
    ResultData<RoomPrice> selectRoomPrice(@RequestBody  Integer id);

    @RequestMapping("/room/updateRoomPriceById")
    ResultData<Boolean> updateRoomPriceById(@RequestBody RoomPrice roomPrice);

    @RequestMapping("/room/selectHotelRoomByRId")
    ResultData<Room> selectHotelRoomByRId(@RequestBody Integer rid);

    @RequestMapping("/room/updateHotelRoomByRId")
    ResultData<Boolean> updateHotelRoomByRId(@RequestBody Room room);
}
