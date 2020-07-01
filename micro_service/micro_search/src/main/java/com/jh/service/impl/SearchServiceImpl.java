package com.jh.service.impl;

import com.jh.entity.Hotel;
import com.jh.feign.HotelFeign;
import com.jh.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements ISearchService {


    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @Autowired
    private HotelFeign hotelFeign;


    /**
     * 创建索引库
     * @return
     */
    @Override
    public boolean createIndex() {
        IndexOperations indexOperations = restTemplate.indexOps(Hotel.class);
        if (!indexOperations.exists()){
            System.out.println("索引库不存在，就进行创建！");
            indexOperations.create();
            //创建索引库的映射类型
            Document document = indexOperations.createMapping();
            indexOperations.putMapping(document);
        }

        return false;
    }

    /**
     * 删除索引库
     * @return
     */
    @Override
    public boolean deleteIndex() {

        IndexOperations indexOperations =  restTemplate.indexOps(Hotel.class);

        return indexOperations.delete();
    }

    /**
     * 添加文档
     * @param hotel
     * @return
     */
    @Override
    public boolean insertDoc(Hotel hotel) {
        Hotel hotel1 = restTemplate.save(hotel);
        return hotel1 != null;
    }

    /**
     * 修改文档
     * @param params
     * @param id 索引的id
     * @return
     */
    @Override
    public boolean updateDoc(Map<String, Object> params, Integer id) {

        Document document = Document.create();
        params.entrySet().forEach(entry->{
            //将map、params中的键值对转成document内容
            document.append(entry.getKey(),entry.getValue());
        });

        UpdateQuery updateQuery = UpdateQuery.builder(id+"")
                .withDocument(document)
                .build();

        UpdateResponse response = restTemplate.update(updateQuery, IndexCoordinates.of("hotel_index"));


        return response.getResult() == UpdateResponse.Result.UPDATED;
    }

    /**
     * 删除文档
     * @param id
     * @return
     */
    @Override
    public boolean deleteDocById(Integer id) {

        String result = restTemplate.delete(id+"",Hotel.class);
        System.out.println("删除的结果"+ result);

        return result != null;
    }

    /**
     * 同步数据库和索引库的信息
     * 将数据库中的数据同步到ES索引库中
     */
    @Override
    public void syncDataBase() {

        //查询数据库
        List<Hotel> hotelList = hotelFeign.hotelList().getData();

        //循环遍历，将hotel添加到ES中
        hotelList.forEach(hotel -> {
            this.insertDoc(hotel);
        });

    }
}
