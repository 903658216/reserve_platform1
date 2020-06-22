package com.jh.controller;

import com.jh.feign.CityFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/system")
@Slf4j
public class SystemController {

    @Autowired
    private CityFeign cityFeign;




//    /**
//     * 跳转到城市添加页面
//     * @return
//     */
//    @RequestMapping("/toCityAdd")
//    public String toCityAdd(){
//        return "cityadd";
//    }
//
//    /**
//     * 添加城市
//     * @return
//     */
//    @RequestMapping("/cityadd")
//    public String cityAdd(City city){
//
//        //调用城市服务，添加城市数据
//        ResultData<Boolean> flag = cityFeign.citySave(city);
//        log.info("调用服务返回结果：" + flag);
//        if (flag.getCode() == ResultData.Code.OK){
//            return "succ";
//        }
//        return "cityerror";
//    }
//
//    /**
//     * 查询城市列表
//     * @return
//     */
//    @RequestMapping("/toCityList")
//    public ModelAndView toCityList(){
//
//        ModelAndView model = new ModelAndView();
//        //调用城市服务，添加城市数据
//        ResultData<List<City>> cityList = cityFeign.cityList();
//        log.info("调用服务返回结果：" + cityList);
//        if (cityList.getCode() == ResultData.Code.OK){
//            model.setViewName("cityList");
//            model.addObject("cityList",cityList);
//            return model;
//        }else{
//
//            model.setViewName("cityerror");
//            return model;
//        }
//    }



//    public static void main(String[] args) {
//    }
}
