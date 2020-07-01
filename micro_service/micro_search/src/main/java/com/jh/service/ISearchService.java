package com.jh.service;


import com.jh.entity.Hotel;

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
}
