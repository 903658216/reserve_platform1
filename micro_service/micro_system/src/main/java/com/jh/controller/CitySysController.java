package com.jh.controller;

import com.jh.entity.City;
import com.jh.entity.ResultData;
import com.jh.feign.CityFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/citySystem")
@Slf4j
public class CitySysController {

    @Autowired
    private CityFeign cityFeign;

    /**
     * 跳转到城市添加页面
     * @return
     */
    @RequestMapping("/toCityAdd")
    public String toCityAdd(){
        return "cityadd";
    }

    /**
     * 添加城市
     * @return
     */
    @RequestMapping("/cityadd")
    public String cityAdd(City city){

        //调用城市服务，添加城市数据
        ResultData<Boolean> flag = cityFeign.citySave(city);
        log.info("调用服务返回结果：" + flag);
        if (flag.getCode() == ResultData.Code.OK){
            return "redirect:/citySystem/toCityList";
        }
        return "error";
    }

    /**
     * 查询城市列表
     * @return
     */
    @RequestMapping("/toCityList")
    public String toCityList(Model model){

        //调用城市服务，添加城市数据
        ResultData<List<City>> cityList = cityFeign.cityList();
        log.info("调用服务返回结果：" + cityList);
        if (cityList.getCode() == ResultData.Code.OK){
            model.addAttribute("cityList",cityList.getData());
            return "cityList";
        }else{

            return "error";
        }
    }

    /**
     * 根据城市的编号跳转到修改界面
     * @param id
     * @return
     */
    @RequestMapping("/toCityUpdate")
    public String toCityUpdate(@RequestParam String id, Model model){


        //调用城市服务，查询城市的信息
        ResultData<City> city = cityFeign.selectCityById(Integer.valueOf(id));
        log.info("调用服务返回结果：" + city);
        if (city.getCode() == ResultData.Code.OK){
            model.addAttribute("city",city.getData());
            return "cityUpdate";
        }

        return "error";
    }

    /**
     * 根据城市编号修改城市信息
     * @param city
     * @return
     */
    @RequestMapping("/cityUpdateById")
    public String cityUpdateById(City city){

        //调用城市服务，查询城市的信息
        ResultData<Boolean> flag = cityFeign.cityUpdateById(city);
        log.info("调用城市服务修改信息返回结果：" + flag);
        if (flag.getCode() == ResultData.Code.OK){
            return "redirect:/citySystem/toCityList";
        }else{

            return "error";
        }

    }

    /**
     * 根据城市的编号删除城市信息
     * @param id
     * @return
     */
    @RequestMapping("/cityDeleteById")
    public String cityDeleteById(@RequestParam String id){

        //调用城市服务，根据城市编号删除城市的信息
        ResultData<Boolean> flag = cityFeign.cityDeleteById(Integer.valueOf(id));
        log.info("调用城市服务删除城市信息返回结果：" + flag);
        if (flag.getCode() == ResultData.Code.OK){
            return "redirect:/citySystem/toCityList";
        }
        return "error";

    }







}
