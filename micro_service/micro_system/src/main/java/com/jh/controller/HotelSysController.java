package com.jh.controller;

import com.jh.entity.City;
import com.jh.entity.Hotel;
import com.jh.entity.ResultData;
import com.jh.entity.Room;
import com.jh.feign.CityFeign;
import com.jh.feign.HotelFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/hotelSystem")
@Slf4j
public class HotelSysController {

    @Autowired
    private CityFeign cityFeign;

    @Autowired
    private HotelFeign hotelFeign;

    /**
     * 图片上传之后存放到的本地磁盘的路径
     */
    private static final String UPLOAD_PATH = "D:\\worker\\images";

    /**
     * 跳转到酒店添加页面
     * @return
     */
    @RequestMapping("/toHotelAdd")
    public String toHotelAdd(Model model){

        ResultData<List<City>> cityList = cityFeign.cityList();
        model.addAttribute("cityList",cityList.getData());
        log.info("跳转到酒店添加页面：");
        return  "hotelAdd";

    }

    /**
     * 查询酒店列表
     * @return
     */
    @RequestMapping("/toHotelList")
    public String toHotelList(Model model){


        ResultData<List<Hotel>> hotelList = hotelFeign.hotelList();
        ResultData<List<City>> cityList = cityFeign.cityList();
        model.addAttribute("hotelList" ,hotelList.getData());
        System.out.println(hotelList.getData());
        System.out.println(cityList.getData());
        model.addAttribute("cityList" ,cityList.getData());
        return "hotelList";
    }

    /**
     * 添加酒店页面
     * @param hotel
     * @return
     */
    @RequestMapping("/hotelAdd")
    public String hotelAdd(Hotel hotel, MultipartFile image){

        //处理上传的文件名
        String fileName = UUID.randomUUID().toString();
        File filePath = new File(UPLOAD_PATH);
        if (!filePath.exists()){
            filePath.mkdirs();
        }

        //根据父路径filePath创建子路径的文件fileName
        File updateFile = new File(filePath,fileName);

        //处理文件上传,jdk7的新特性，只要实现了Closeable接口的都可以将其创建的语句放到try的括号里面，它会替我们自动关闭资源
        try (  InputStream in = image.getInputStream();
               OutputStream out = new FileOutputStream(updateFile);
               ){
          //上传
            IOUtils.copy(in,out);

        }catch (IOException e){
            e.printStackTrace();
        }

        //设置酒店信息的图片文件名
//        hotel.setHotelImage(updateFile.getAbsolutePath());
        //设置图片的上传路径
        hotel.setHotelImage(updateFile.getAbsolutePath().replace("\\", "/"));
        ResultData<Boolean> result = hotelFeign.insertHotel(hotel);

        if (result.getCode() == ResultData.Code.OK){
            log.info("添加酒店成功");
           return "redirect:/hotelSystem/toHotelList";
       }

       return "error";
    }



    /**
     * 根据图片路径，获取本地的图片
     * @param path
     * @return
     */
    @RequestMapping("/getImage")
    public void getImage(String path, HttpServletResponse response){
        try (
                InputStream in = new FileInputStream(path);
                OutputStream out = response.getOutputStream();
        ){
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到修改酒店页面
     * @return
     */
    @RequestMapping("/toUpdateHotel")
    public ModelAndView updateHotelById(@RequestParam String id){
        ModelAndView modelAndView =new ModelAndView();
        log.info("酒店的编号为："+id);
        //查询城市列表信息
        ResultData<List<City>> cityList = cityFeign.cityList();

        ResultData<Hotel> hotel = hotelFeign.seleteHotelById(Integer.valueOf(id));
        modelAndView.setViewName("updateHotel");
        modelAndView.addObject("cityList",cityList.getData());
        modelAndView.addObject("hotel",hotel.getData());

        return modelAndView;
    }

    /**
     * 根据酒店编号修改酒店信息
     * @param hotel
     * @param image
     * @return
     */
    @RequestMapping("/updateHotelById")
    public String updateHotelById(Hotel hotel,MultipartFile image,String ocid){

        //如果修改了酒店图片
        if (!image.isEmpty()){
            //处理上传的文件名
            String fileName = UUID.randomUUID().toString();
            File filePath = new File(UPLOAD_PATH);
            if (!filePath.exists()){
                filePath.mkdirs();
            }

            //根据父路径filePath创建子路径的文件fileName
            File updateFile = new File(filePath,fileName);

            //处理文件上传,jdk7的新特性，只要实现了Closeable接口的都可以将其创建的语句放到try的括号里面，它会替我们自动关闭资源
            try (  InputStream in = image.getInputStream();
                   OutputStream out = new FileOutputStream(updateFile);
            ){
                //上传
                IOUtils.copy(in,out);

            }catch (IOException e){
                e.printStackTrace();
            }

            //设置图片的上传路径和图片文件名
            hotel.setHotelImage(updateFile.getAbsolutePath().replace("\\", "/"));
        }

        //调用酒店服务，修改酒店信息
        ResultData<Boolean> result = hotelFeign.updateHotelById(hotel,Integer.valueOf(ocid));

        if (result.getCode() == ResultData.Code.OK){
            log.info("修改酒店信息成功");
            return "redirect:/hotelSystem/toHotelList";
        }

        return "error";
    }

    /**
     * 根据酒店编号删除酒店信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteHotelById")
    public String deleteHotelById(String id){

        //调用酒店服务，修改酒店信息
        ResultData<Boolean> result = hotelFeign.deleteHotelById(Integer.valueOf(id));

        if (result.getCode() == ResultData.Code.OK){
            log.info("修改酒店信息成功");
            return "redirect:/hotelSystem/toHotelList";
        }

        return "error";
    }

    /**
     * 根据酒店编号，查找客房信息
     * @return
     */
    @RequestMapping("/selectHotelRTById")
    public  ModelAndView selectHotelRTById(String id){

        ModelAndView modelAndView = new ModelAndView();
        //调用酒店服务，根据酒店编号查询其所拥有的客房信息
        ResultData<List<Room>> result = hotelFeign.selectRoomByHid(Integer.valueOf(id));

        if (result.getCode() == ResultData.Code.OK){
            log.info("成功返回客房管理页面");
            modelAndView.addObject("room",result.getData());

        }
        return modelAndView;
    }


}
