package com.jh.feign;

import com.jh.entity.City;
import com.jh.entity.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;



@FeignClient("micro-city")
public interface CityFeign {

    @RequestMapping("/city/save")
    ResultData<Boolean> citySave(@RequestBody City city);

    @RequestMapping("/city/list")
    ResultData<List<City>> cityList();

    @RequestMapping("/city/selectCityById")
    ResultData<City> selectCityById(@RequestBody Integer id);

    @RequestMapping("/city/cityUpdateById")
    ResultData<Boolean> cityUpdateById(@RequestBody City city);

    @RequestMapping("/city/cityDeleteById")
    ResultData<Boolean> cityDeleteById(@RequestBody Integer id);
}
