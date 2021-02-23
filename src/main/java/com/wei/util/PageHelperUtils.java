package com.wei.util;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName:
 * @Description:分页工具类
 * @author:
 * @date:ydy2020-08-13 8:57
 */
public class PageHelperUtils {

    public static void startPage(PageInfo pageInfo){
        if(StringUtils.isEmpty(pageInfo.getPagemodel())){ //分页该字段有值
            PageHelper.startPage(pageInfo.getNowpage(), pageInfo.getPagesize());
        }
    }

    public static void startPage(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
    }
}
