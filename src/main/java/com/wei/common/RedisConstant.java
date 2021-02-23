package com.wei.common;

/**
 * @ClassName RedisConstent
 * @Description : redis相关常量
 * @Author weijunjie
 * @Date 2020/8/12 15:04
 */
public class RedisConstant {

    public static final Integer REDIS_TIME_1 = 1;

    public static final Integer REDIS_TIME_30 = 30;

    public static final Integer REDIS_TIME_24 = 24;

    public static final Long USER_POWER_REFRESH = 10*60*1000L;

    public static final Long REDIS_NUMBER_1= -1L;

    public static final Long REDIS_NUMBER_2= -2L;

    public static final Long REDIS_NUMBER_6= 6L;

    //用户-token保存Redis前缀
    public static final String USER_JWT_TOKEN = "user:jwt:";

    //用户-权限保存redis前缀
    public static final String USER_POWER_TOKEN = "user:power:";

    //用户-权限保存redis前缀
    public static final String USER_REPEAT_REQUEST = "user:repeat:";

    //机构名-code映射前缀
    public static final String ORGAN_NAME_CODE = "organ:code:";

    //字典表常量配置查询
    public static final String DICT_VALUE = "dict:value:";

    //抽样
    public static final String SAMPLING_FILTER_COMBOBOX = "sampling:filterCombo:";
    public static final String SAMPLING_FILTER_STATISTICS = "sampling:filterStatistics:";



}
