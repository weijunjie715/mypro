package com.wei.util;

import java.util.Date;
import java.util.Random;

/**
 * @ClassName CodeUtil
 * @Description : 生成编码code工具类
 * @Author weijunjie
 * @Date 2020/8/7 8:58
 */
public class CodeUtil {



    public static final String TYPE_ORGAN = "organ";
    public static final String TYPE_ROLE = "role";
    public static final String TYPE_USER = "user";
    public static final String TYPE_FILE_UPLOAD = "fileUpload";
    public static final String TYPE_SAMPLING = "sampling";
    public static final String TYPE_SAMPLE = "sample";
    public static final String TYPE_CHECK = "";
    public static final String TYPE_CHECK_LOG = "";
    public static final String TYPE_POWER = "";
    public static final String TYPE_FILE_KEEP = "";
    public static final String TYPE_SYS_FORM = "sysForm";
    public static final String TYPE_SYS_CLASS = "sysClass";
    public static final String TYPE_USER_CLASS = "userClass";
    public static final String TYPE_DICT_TYPE = "dictType";
    public static final String TYPE_ROLE_CLASS = "";
    public static final String TYPE_SAMPLE_REPORT = "report";

    public static final Integer RANDOM_MAX = 9;

    public static final Integer RANDOM_LENGTH_2 = 2;

    public static String createCode(String type,Integer l){
        StringBuffer sb = new StringBuffer();
        String timeCode = DateUtil.formatDate(new Date(), "MMddHHmmssSSSS");
        sb.append(type);
        sb.append(timeCode);
        createRandom(l,sb);
        return sb.toString();
    }

    public static String createTimeCode(String type,Integer l){
        StringBuffer sb = new StringBuffer();
        String timeCode = new Date().getTime()+"";
        sb.append(type);
        sb.append(timeCode);
        createRandom(l,sb);
        return sb.toString();
    }

    public static String createCode(String type,Integer l,String organCode){
        StringBuffer sb = new StringBuffer();
        String timeCode = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
        sb.append(type);
        sb.append(organCode);
        sb.append(timeCode);
        createRandom(l,sb);
        return sb.toString();
    }

    private static void createRandom(int l,StringBuffer sb){
        Random random = new Random();
        for(int i = 0;i<l;i++){
            int z = random.nextInt(RANDOM_MAX);
            sb.append(z);
        }
    }

    @SuppressWarnings("all")
    public static void main(String args[]){
        long l1 = System.currentTimeMillis();
        String user = createCode(null, 0);
        long l2 = System.currentTimeMillis();
        System.out.println(user);
        System.out.println(l2-l1);
    }

}
