package com.jh.controller;

import com.jh.entity.ResultData;
import com.jh.entity.Room;
import com.jh.entity.RoomPrice;
import com.jh.service.IRoomPriceService;
import com.jh.service.IRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
@Slf4j
public class RoomController {

    @Autowired
    private IRoomService iRoomService;

    @Autowired
    private IRoomPriceService iRoomPriceService;

    /**
     * 根据客房类型编号查询酒店id
     * @param rid 酒店客房类型编号
     * @return  ResultData<Hotel>
     */
    @RequestMapping("/selectHotelIdByRoomId")
    ResultData<Room> selectHotelIdByRoomId(@RequestBody Integer rid){

        Room room = iRoomService.getById(rid);

        return new ResultData<Room>().setData(room);



    }

    /**
     * 添加酒店客房类型
     * @param room
     * @return
     */
    @RequestMapping("/insertHotelRoom")
    ResultData<Boolean> insertHotelRoom(@RequestBody Room room){

        boolean flag = iRoomService.save(room);
        return new ResultData<Boolean>().setData(flag);
    }

    /**
     * 根据酒店编号查询酒店客房类型信息
     * @param hid
     * @return
     */
    @RequestMapping("/selectRoomListByHid")
    ResultData<List<Room>> selectRoomListByHid(@RequestParam Integer hid){

        List<Room> roomList = iRoomService.selectRoomListByHid(hid);
//        List<Room> roomList = iHotelService.
//        Map<String, Integer> map =new HashMap<>();
//        map.put("hid",hid);
        return new ResultData<List<Room>>().setData(roomList);
    }

    /**
     * 根据客房编号查询客房信息
     * @param rid
     * @return
     */
    @RequestMapping("/selectHotelRoomByRId")
    ResultData<Room> selectHotelRoomByRId(@RequestBody Integer rid){
        Room room = iRoomService.getById(rid);
        return new ResultData<Room>().setData(room);
    }


    /**
     * 根据客房编号查询客房价格列表
     * @param rid
     * @return
     */
    @RequestMapping("/selectRoomPriceListByRid")
    ResultData<List<RoomPrice>> selectRoomPriceListByRid(@RequestBody Integer rid){

        List<RoomPrice> roomPriceList = iRoomPriceService.selectRoomPriceListByRid(rid);

        return new ResultData<List<RoomPrice>>().setData(roomPriceList);
    }

    /**
     * 根据客房价格表的编号查询价格表
     * @param id
     * @return
     */
    @RequestMapping("/selectRoomPrice")
    ResultData<RoomPrice> selectRoomPrice(@RequestBody  Integer id){

        return new ResultData<RoomPrice>().setData(iRoomPriceService.getById(id));
    }

    /**
     * 根据客房价格表的编号修改客房价格信息
     * @param roomPrice
     * @return
     */
    @RequestMapping("/updateRoomPriceById")
    ResultData<Boolean> updateRoomPriceById(@RequestBody RoomPrice roomPrice){

        boolean flag = iRoomPriceService.updateById(roomPrice);
        return new ResultData<Boolean>().setData(flag);
    }


    /**
     * 根据客房编号修改客房信息
     * @param room
     * @return
     */
    @RequestMapping("/updateHotelRoomByRId")
    ResultData<Boolean> updateHotelRoomByRId(@RequestBody Room room){

        boolean flag = iRoomService.updateById(room);
        return new ResultData<Boolean>().setData(flag);
    }
}
