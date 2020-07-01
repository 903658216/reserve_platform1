package com.jh.micro_search;

import com.jh.service.ISearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MicroSearchApplicationTests {

    @Autowired
    private ISearchService iSearchService;

    /**
     * 测试代码实现连接ES的创建索引、映射、修改、删除、同步数据库中的数据到ES中
     */
    @Test
    void contextLoads() {
        //开始测试索引库的操作
        //创建索引库、映射类型
//        iSearchService.createIndex();

        //添加文档
//        Hotel hotel = new Hotel()
//                .setId(1)
//                .setHotelImage("http://www.baidu.com")
//                .setAddress("广东省深圳市福田路28号12312312")
//                .setBrand("8天123123")
//                .setDistrict("福田区123123")
//                .setHotelInfo("快捷酒店123123")
//                .setHotelName("福田区7天优品连锁快捷品牌酒店123")
//                .setKeyword("快捷,便宜,方便,临近地铁,考场周边123")
//                .setOpenTime(new Date())
//                .setStar(0)
//                .setType(1);
//
//        iSearchService.insertDoc(hotel);

        //修改酒店
//        Map<String, Object> map = new HashMap<>();
//        map.put("hotelName", "修改后的酒店");

//        iSearchService.updateDoc(map, 1);

        //删除酒店
//        iSearchService.deleteDocById(1);

        //将数据库中的酒店数据同步到ES中
        iSearchService.syncDataBase();
    }


    /**
     * 测试elasticSearch的一些指令通过代码的方式来实现
     */
    @Test
    void commandTest(){



    }




}
