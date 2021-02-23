package com.wei.config;

import com.wei.service.SysErrorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MyErrorAttributes
 * 描述 :
 * @Author weijunjie
 * @Date 2020/6/23 9:03
 */
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {

    @Autowired
    private SysErrorLogService sysErrorLogService;

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        //DefaultErrorAttributes的错误数据
        Map<String, Object> map = super.getErrorAttributes(requestAttributes, includeStackTrace);
        //记录所有请求异常入库
        sysErrorLogService.saveErrorLog(map);
        //系统异常 统一返回
        Map<String, Object> mapRes = new HashMap<>();
        mapRes.put("code",map.get("status")+"");
        mapRes.put("msg","请求异常，联系管理员");
        return mapRes;
    }
}
