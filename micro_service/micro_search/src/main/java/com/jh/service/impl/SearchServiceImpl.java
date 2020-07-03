package com.jh.service.impl;

import com.jh.entity.Hotel;
import com.jh.entity.Room;
import com.jh.entity.RoomPrice;
import com.jh.feign.HotelFeign;
import com.jh.service.ISearchService;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
       //如果索引库不存在才会构建
        if (!indexOperations.exists()){
            System.out.println("索引库不存在，就进行创建！");
            indexOperations.create();
            //创建索引库的映射类型
            Document document = indexOperations.createMapping();
            indexOperations.putMapping(document);

            //将数据库中的数据同步到索引库中
            syncDataBase();
        }
//        System.out.println("索引库已经存在，不创建索引库");

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

    @Override
    public boolean insertRoom(Room room) {
        //将Room映射成Map对象
        Map<String,Object> roomMap = new HashMap<>();
        roomMap.put("id",room.getId());
        roomMap.put("hid",room.getHid());
        roomMap.put("title",room.getTitle());
        roomMap.put("info",room.getInfo());
        roomMap.put("number",room.getNumber());
        roomMap.put("roomPriceList",new ArrayList<>());

        Map<String,Object> params = new HashMap<>();
        params.put("roomInfo",roomMap);

        UpdateQuery updateQuery = UpdateQuery.builder(room.getHid()+"")
                .withScript("ctx._source.roomList.add(params.roomInfo)")
                .withParams(params)
                .build();

        UpdateResponse updateResponse = restTemplate.update(updateQuery,IndexCoordinates.of("hotel_index"));
        return updateResponse.getResult() == UpdateResponse.Result.UPDATED;
    }

    /**
     * 新增酒店客房类型时，对应的客房价格
     * @param hid
     * @param roomPriceList
     * @return
     */
    @Override
    public boolean insertRoomPrice(Integer hid,List<RoomPrice> roomPriceList){

        List<UpdateQuery> updateQueryList = new ArrayList<>();

        //循环客房价格集合
        roomPriceList.stream().forEach(roomPrice -> {

            //将RoomPrice映射成map集合
            Map<String,Object> roomPriceMap = new HashMap<>();
            roomPriceMap.put("id",roomPrice.getId());
            roomPriceMap.put("rid",roomPrice.getRid());
            roomPriceMap.put("price",roomPrice.getPrice());
            roomPriceMap.put("number",roomPrice.getNumber());
            roomPriceMap.put("hasNumber",roomPrice.getHasNumber());
            roomPriceMap.put("date",new SimpleDateFormat("yyyy-MM-dd").format(roomPrice.getDate()));

            //脚本的参数集合
            Map<String,Object> params = new HashMap<>();
            params.put("rid",roomPrice.getRid());
            params.put("priceInfo",roomPriceMap);

            String script ="for(r in ctx._source.roomList){ if(r.id == params.rid){r.roomPriceList.add(params.priceInfo); } }";
            UpdateQuery updateQuery = UpdateQuery.builder(hid+"")
                    .withScript(script)
                    .withParams(params)
                    .build();

            updateQueryList.add(updateQuery);

            });
        //进行批量的更改
        restTemplate.bulkUpdate(updateQueryList,IndexCoordinates.of("hotel_index"));

        return true;
    }

    /**
     * 调整酒店客房价格的信息
     * @param hid
     * @param roomPrice
     * @return
     */
    @Override
    public boolean updateRoomPrice(Integer hid,RoomPrice roomPrice){

        Map<String,Object> roomPriceMap = new HashMap<>();
        roomPriceMap.put("rid",roomPrice.getRid());
        roomPriceMap.put("roomPriceId",roomPrice.getId());
        roomPriceMap.put("roomPricePrice",roomPrice.getPrice());
//String script1 = "for(r in ctx._source.rooms){ if(r.id == params.rid) { for(rp in r.prices) { if(rp.id == params.rpid) { rp.price = params.price; } } } }";
        String script = "for(r in ctx._source.roomList){ if(r.id == params.rid ){ for(rp in r.roomPriceList){ if(rp.id == params.roomPriceId){rp.roomPriceList = params.roomPricePrice} } } }";
        UpdateQuery updateQuery = UpdateQuery.builder(hid+"")
                .withScript(script)
                .withParams(roomPriceMap)
                .build();

        UpdateResponse response = restTemplate.update(updateQuery,IndexCoordinates.of("hotel_index"));
        return true;
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

            List<Room> roomList = hotelFeign.selectRoomListByHid(hotel.getId()).getData();
            hotel.setRoomList(roomList != null && roomList.size()>0? roomList:hotel.getRoomList());
           roomList.stream().forEach(room -> {
               List<RoomPrice> roomPriceList = hotelFeign.selectRoomPriceListByRid(room.getId()).getData();
               room.setRoomPriceList(roomPriceList != null && roomPriceList.size()>0 ? roomPriceList : room.getRoomPriceList());
           });
            this.insertDoc(hotel);
        });

    }

    /**
     * 搜索酒店信息
     * @param queryBuilder
     * @return
     */
    @Override
    public List<Hotel> search(QueryBuilder queryBuilder) {
        //本地（索引库）搜索查询
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryBuilder);
        //进行分页的设置
//        PageRequest pageRequest = PageRequest.of(0,2);
//        nativeSearchQuery.setPageable(pageRequest);


        //进行排序的设置--根据id进行升序排序
//        nativeSearchQuery.addSort(Sort.by(Sort.Order.asc("id")));

        //设置按照距离排序
        Sort.Order myLocation = new GeoDistanceOrder("myLocation",new GeoPoint(22.625555,113.851466))
                //根据平面的方式
                .with(GeoDistanceOrder.DistanceType.plane)
                //设置单位
                .withUnit("km");

        //进行高亮设置
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.field("hotelName").preTags("<font color='red'>").postTags("</font>");
//        nativeSearchQuery.setHighlightQuery(new HighlightQuery(highlightBuilder));


        //进行索引查询
        SearchHits<Hotel> hits = restTemplate.search(nativeSearchQuery,Hotel.class);
        //总共命中了几个记录
        long total = hits.getTotalHits();
        System.out.println("符合本次查询的文档总共有："+total+"个");
        //查询结果中，最高评分是
        float maxScore = hits.getMaxScore();
        System.out.println("本次查询中，最高文档的评分是："+maxScore);


        List<Hotel> hotelList = new ArrayList<>();
        hits.getSearchHits().stream().forEach( hotelSearchHit -> {

            //hotel的查询结果为
            String id =hotelSearchHit.getId();
            Hotel hotel = hotelSearchHit.getContent();

            List<Object> sortValues = hotelSearchHit.getSortValues();
            System.out.println("排序的字段为:"+sortValues);

            List<String> hotelName =hotelSearchHit.getHighlightField("hotelName");
            hotel.setHotelName(hotelName !=null && hotelName.size() > 0 ? hotelName.get(0) : hotel.getHotelName());
            hotelList.add(hotel);
        });

        return hotelList;
    }
}
