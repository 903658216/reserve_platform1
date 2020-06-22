package com.jh.exception;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class SystemException implements ErrorController {

    /**
     * 处理系统异常
     * @return
     */
    @RequestMapping("/error")
    public String systemHandler(HttpServletResponse response){

        //获得系统异常的响应码
        int status = response.getStatus();

        switch (status){
            case 404:
                return "404";
        }

        return "myerror";
    }

    /**
     * 出现系统异常（没有到达controller的请求的异常）
     * @return
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
