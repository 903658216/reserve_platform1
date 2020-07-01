package com.jh.feign;

import com.jh.entity.City;
import com.jh.entity.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;



@FeignClient("micro-city")
public interface CityFeign {

    /**
     * 调用城市服务，新增城市信息
     * @param city
     * @return
     */
    @RequestMapping("/city/save")
    ResultData<Boolean> citySave(@RequestBody City city);

    /**
     * 调用城市服务，查询城市列表
     * @return
     */
    @RequestMapping("/city/list")
    ResultData<List<City>> cityList();

    /**
     * 调用城市服务，根据城市的编号查询城市的信息
     * @param id
     * @return
     */
    @RequestMapping("/city/selectCityById")
    ResultData<City> selectCityById(@RequestBody Integer id);

    /**
     * 调用城市服务，根据城市的编号修改城市信息
     * @param city
     * @return
     */
    @RequestMapping("/city/cityUpdateById")
    ResultData<Boolean> cityUpdateById(@RequestBody City city);

    /**
     * 调用城市服务，根据城市的编号删除城市信息
     * @param id
     * @return
     */
    @RequestMapping("/city/cityDeleteById")
    ResultData<Boolean> cityDeleteById(@RequestBody Integer id);

    /**
     * 调用城市服务，根据城市的编号和酒店的数量来修改城市所拥有的酒店数量
     * @param cId
     * @param number
     * @return
     */
    @RequestMapping("/city/cityUpdateHoterNumberById/{cId}/{number}")
    ResultData<Boolean> cityUpdateHoterNumberById(@PathVariable Integer cId, @PathVariable Integer number);



}
