package com.wei.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName: ParamsUtil
 * @Description:获得前端传参数的工具类
 * @author:
 * @date:
 */
public class ParamsUtil {


    private static Logger logger = LoggerFactory.getLogger(ParamsUtil.class);

    private static final String NOWPAGE = "nowpage"; //第几页

    private static final String PAGESIZE = "pagesize"; //每页条数

    private static final String SORT = "sort"; //排序字段

    private static final String ORDER = "order"; //排序方式

    private static final String PAGEMODEL = "pagemodel"; //是否查询全部,不为空查询全部,不分页


    /**
     */
    public static String getErrorMsg(Exception e) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        e.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }

}
