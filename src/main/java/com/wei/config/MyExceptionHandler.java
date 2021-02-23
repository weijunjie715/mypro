package com.wei.config;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(UnavailableException.class)
    public String unauthorizedException(HttpServletRequest req, Exception e){
        Map<String,Object> map = new HashMap<>();
        req.setAttribute("javax.servlet.error.status_code",500);
        map.put("code","999");
        map.put("message","访问异常");
        //将自定义错误数据放入request中
        req.setAttribute("ext",map);
        return "forward:/error";
    }
}
