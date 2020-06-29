package com.jh.controller;

import com.jh.entity.*;
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
     * 客房类型图片上传之后存放到的本地磁盘的路径
     */
    private static final String UPLOAD_TYPE_PATH = "D:\\worker\\room\\images";

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

        ResultData<Hotel> hotel = hotelFeign.selectHotelById(Integer.valueOf(id));
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

//    /**
//     * 根据酒店编号，查找客房信息
//     * @return
//     */
//    @RequestMapping("/selectHotelRTById")
//    public  ModelAndView selectHotelRTById(String id){
//
//        ModelAndView modelAndView = new ModelAndView();
//        //调用酒店服务，根据酒店编号查询其所拥有的客房信息
//        ResultData<List<Room>> result = hotelFeign.selectRoomByHid(Integer.valueOf(id));
//
//        if (result.getCode() == ResultData.Code.OK){
//            log.info("成功返回客房管理页面");
//            modelAndView.addObject("room",result.getData());
//
//        }
//        return modelAndView;
//    }

    /**
     * 跳转到添加酒店客房类型页面
     * @param id 酒店编号
     * @param model
     * @return
     */
    @RequestMapping("/toHotelRoomById")
    public String toHotelRoom(@RequestParam String id,Model model){
        model.addAttribute("hid",id);

        return  "hotelRoomAdd";
    }

    /**
     * 添加酒店客房类型
     * @return
     */
    @RequestMapping("/hotelRoomAdd")
    public String hotelRoomAdd(Room room,MultipartFile image1){


        //处理上传的文件名
        String fileName = UUID.randomUUID().toString();
        File filePath = new File(UPLOAD_TYPE_PATH);
        if (!filePath.exists()){
            filePath.mkdirs();
        }

        //根据父路径filePath创建子路径的文件fileName
        File updateFile = new File(filePath,fileName);

        //处理文件上传,jdk7的新特性，只要实现了Closeable接口的都可以将其创建的语句放到try的括号里面，它会替我们自动关闭资源
        try (  InputStream in = image1.getInputStream();
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
        room.setImage(updateFile.getAbsolutePath().replace("\\", "/"));
        ResultData<Boolean> result = hotelFeign.insertHotelRoom(room);

        if (result.getCode() == ResultData.Code.OK){
            log.info("添加酒店客房类型成功");
            return "redirect:/hotelSystem/selectHotelRoomListByHid?hid="+room.getHid();
        }
        return "error";

    }

    /**
     * 根据酒店的编号，查询客房类型的信息
     * @param hid
     * @return
     */
    @RequestMapping("/selectHotelRoomListByHid")
    public String selectHotelRoomListByHid(@RequestParam Integer hid,Model model){

        //根据酒店的编号，查询客房的类型
        ResultData<List<Room>> rooms = hotelFeign.selectRoomListByHid(hid);
//        model.addAttribute("rooms",rooms);

        if (rooms.getCode() == ResultData.Code.OK){
            log.info("成功返回客房管理页面");
            ResultData<List<Hotel>> hotelList = hotelFeign.hotelList();
            model.addAttribute("roomList",rooms.getData());
            model.addAttribute("hotelList",hotelList.getData());
            System.out.println("兄弟，你成功了。。。。。。。。。。。");
            return "hotelRoomList";
        }

        return "error";
    }


    /**
     * 根据客房编号跳转到酒店客房修改页面
     * @param rid
     * @return
     */
    @RequestMapping("/toRoomUpdateByRid")
    public String toRoomUpdateByRid(@RequestParam Integer rid ,Model model){

        ResultData<Room> roomById = hotelFeign.selectHotelRoomByRId(rid);
        if (roomById.getCode() == ResultData.Code.OK){
            log.info("成功返回客房修改页面");
            model.addAttribute("room",roomById.getData());
            return "updateRoom";
        }

        return "error";


    }

    /**
     * 跳转到价格展示列表
     * @param rid
     * @return
     */
    @RequestMapping("/toRoomPriceUpdate")
    public String toRoomPriceUpdate(@RequestParam Integer rid,Model model){

        ResultData<List<RoomPrice>> roomPriceListByRid = hotelFeign.selectRoomPriceListByRid(rid);
        if (roomPriceListByRid.getCode() == ResultData.Code.OK){
            log.info("成功返回客房价格展示页面");
            model.addAttribute("roomPriceList",roomPriceListByRid.getData());
            return "roomPriceList";
        }

        return "error";
    }

    /**
     * 根据客房价格表的编号跳转到修改价格页面
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateRoomPriceByRPid")
    public String toUpdateRoomPriceByRPid(@RequestParam Integer id,Model model){

        ResultData<RoomPrice> roomPriceById = hotelFeign.selectRoomPrice(id);
        if (roomPriceById.getCode() == ResultData.Code.OK){
            log.info("成功返回客房价格修改展示页面");
            model.addAttribute("roomPrice",roomPriceById.getData());
            return "updateRoomPrice";
        }

        return "error";

    }

    /**
     * 根据客房价格编号修改客房的价格信息
     * @param roomPrice
     * @return
     */
    @RequestMapping("/updateRoomPriceById")
    public String updateRoomPriceById(RoomPrice roomPrice){

        //调用酒店服务，修改酒店信息
        ResultData<Boolean> result = hotelFeign.updateRoomPriceById(roomPrice);

        if (result.getCode() == ResultData.Code.OK){
            log.info("修改酒店信息成功");
            return "redirect:/hotelSystem/toRoomPriceUpdate?rid="+roomPrice.getRid();
        }

        return "error";
    }




}
