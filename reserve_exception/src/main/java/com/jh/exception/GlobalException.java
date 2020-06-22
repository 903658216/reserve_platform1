package com.jh.exception;


import com.jh.entity.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object exceptionHandler(Exception e, HttpServletRequest request){
        log.error("统一异常处理！", e);

        ModelAndView modelAndView = new ModelAndView();

        String header = request.getHeader("X-Requested-With");
        if (header != null && header.equals("XMLHttpRequest")){
            System.out.println("异步请求！！！");
            //异步请求 - ResultData
            return new ResultData<>().setCode(ResultData.Code.ERROR).setMsg("服务器繁忙，请稍后再试！");
        }

        modelAndView.setViewName("myerror");
        //同步请求
        return modelAndView;
    }
}
