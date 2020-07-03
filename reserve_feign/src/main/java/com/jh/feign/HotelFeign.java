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


    /**
     * 新增酒店信息
     * @param hotel
     * @return
     */
    @RequestMapping("/hotel/save")
    ResultData<Boolean> insertHotel(@RequestBody Hotel hotel);

    /**
     * 查询酒店列表集合
     * @return
     */
    @RequestMapping("/hotel/list")
    ResultData<List<Hotel>> hotelList();

    /**
     * 根据酒店编号查询酒店信息
     * @param id
     * @return
     */
    @RequestMapping("/hotel/selectHotelById")
    ResultData<Hotel> selectHotelById(@RequestBody Integer id);

    /**
     * 根据酒店编号修改酒店信息
     * @param hotel
     * @param ocid
     * @return  ResultData<Boolean>
     */
    @RequestMapping("/hotel/updateHotelById")
    ResultData<Boolean> updateHotelById(@RequestBody Hotel hotel,@RequestParam Integer ocid);

    /**
     * 根据酒店编号删除酒店信息
     * @param id
     * @return ResultData<Boolean>
     */
    @RequestMapping("/hotel/deleteHotelById")
    ResultData<Boolean> deleteHotelById(@RequestParam Integer id);

    /**
     * 根据酒店编号查询客房类型列表集合信息
     * @param hid
     * @return ResultData<List<Room>>
     */
    @RequestMapping("/room/selectRoomListByHid")
    ResultData<List<Room>> selectRoomListByHid(@RequestParam Integer hid);

    /**
     * 新增酒店客房类型信息
     * @param room
     * @return ResultData<Boolean>
     */
    @RequestMapping("/room/insertHotelRoom")
    ResultData<Boolean> insertHotelRoom(@RequestBody  Room room);

    /**
     * 根据客房类型编号，查询客房价格列表集合信息
     * @param rid
     * @return ResultData<List<RoomPrice>>
     */
    @RequestMapping("/room/selectRoomPriceListByRid")
    ResultData<List<RoomPrice>> selectRoomPriceListByRid(@RequestBody Integer rid);

    /**
     * 根据客房价格表编号查询客房价格信息
     * @param id
     * @return ResultData<RoomPrice>
     */
    @RequestMapping("/room/selectRoomPrice")
    ResultData<RoomPrice> selectRoomPrice(@RequestBody  Integer id);

    /**
     * 根据客房价格表编号修改价格信息
     * @param roomPrice
     * @return  ResultData<Boolean>
     */
    @RequestMapping("/room/updateRoomPriceById")
    ResultData<Boolean> updateRoomPriceById(@RequestBody RoomPrice roomPrice);

    /**
     * 根据客房类型编号查询客房信息
     * @param rid
     * @return ResultData<Room>
     */
    @RequestMapping("/room/selectHotelRoomByRId")
    ResultData<Room> selectHotelRoomByRId(@RequestBody Integer rid);

    /**
     * 根据客房类型编号修改客房类型信息
     * @param room
     * @return ResultData<Boolean>
     */
    @RequestMapping("/room/updateHotelRoomByRId")
    ResultData<Boolean> updateHotelRoomByRId(@RequestBody Room room);

    /**
     * 根据客房类型编号查询酒店信息
     * @param rid 酒店客房类型编号
     * @return  ResultData<Hotel>
     */
    @RequestMapping("/room/selectHotelIdByRoomId")
    ResultData<Room> selectHotelIdByRoomId(@RequestBody Integer rid);


}
