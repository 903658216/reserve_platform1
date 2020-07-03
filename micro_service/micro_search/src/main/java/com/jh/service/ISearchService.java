package com.jh.service;


import com.jh.entity.Hotel;
import com.jh.entity.Room;
import com.jh.entity.RoomPrice;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;
import java.util.Map;

public interface ISearchService {

    /**
     * 创建索引库
     * @return
     */
    boolean createIndex();

    /**
     * 删除索引库
     * @return
     */
    boolean deleteIndex();

    /**
     * 添加文档
     * @param hotel
     * @return
     */
    boolean insertDoc(Hotel hotel);

    /**
     * 新增酒店客房类型信息
     * @param room
     * @return
     */
    boolean insertRoom(Room room);

    /**
     * 新增客房每日价格信息
     * @param hid
     * @param roomPriceList
     * @return
     */
    boolean insertRoomPrice(Integer hid,List<RoomPrice> roomPriceList);


    /**
     * 调整酒店客房价格的信息
     * @param hid
     * @param roomPrice
     * @return
     */
    boolean updateRoomPrice(Integer hid,RoomPrice roomPrice);
    /**
     * 修改文档
     * @param params
     * @param id
     * @return
     */
    boolean updateDoc(Map<String, Object> params, Integer id);

    /**
     * 删除文档
     * @param id
     * @return
     */
    boolean deleteDocById(Integer id);

    /**
     * 同步数据库和索引库的信息
     */
    void syncDataBase();

    /**
     * 搜索酒店信息
     * @param queryBuilder
     * @return
     */
    List<Hotel> search(QueryBuilder queryBuilder);
}
