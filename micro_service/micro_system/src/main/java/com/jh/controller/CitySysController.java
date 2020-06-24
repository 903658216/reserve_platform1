package com.jh.controller;

import com.jh.entity.City;
import com.jh.entity.ResultData;
import com.jh.feign.CityFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
            return "succ";
        }
        return "cityerror";
    }

    /**
     * 查询城市列表
     * @return
     */
    @RequestMapping("/toCityList")
    public ModelAndView toCityList(){

        ModelAndView model = new ModelAndView();
        //调用城市服务，添加城市数据
        ResultData<List<City>> cityList = cityFeign.cityList();
        log.info("调用服务返回结果：" + cityList);
        if (cityList.getCode() == ResultData.Code.OK){
            model.setViewName("cityList");
            model.addObject("cityList",cityList.getData());
            return model;
        }else{

            model.setViewName("cityerror");
            return model;
        }
    }

    /**
     * 根据城市的编号跳转到修改界面
     * @param id
     * @return
     */
    @RequestMapping("/toCityUpdate")
    public ModelAndView toCityUpdate(@RequestParam String id){


        ModelAndView model = new ModelAndView();
        //调用城市服务，查询城市的信息
        ResultData<City> city = cityFeign.selectCityById(Integer.valueOf(id));
        log.info("调用服务返回结果：" + city);
        if (city.getCode() == ResultData.Code.OK){
            model.setViewName("cityUpdate");
            model.addObject("city",city.getData());
            return model;
        }else{

            model.setViewName("cityerror");
            return model;
        }

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

            return "cityerror";
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
        return "cityerror";

    }







}
