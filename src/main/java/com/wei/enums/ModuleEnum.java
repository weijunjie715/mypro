package com.wei.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName ModuleEnum
 * @Description : 模块枚举类
 * @Author weijunjie
 * @Date 2020/8/17 15:35
 */
public enum ModuleEnum {

    USER_MODULE("userModule","用户模块"),
    ROLE_MODULE("roleModule","角色模块"),
    AUTH_MODULE("authModule","权限模块"),
    DICT_MODULE("dictModule","字典模块"),
    ;

    /**
     * @Description 获取前端返回数据对象
     * @Author: weijunjie
     * @Date: 2020/8/18 11:28
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public static JSONObject getResObj() {
        JSONObject jsonObject = new JSONObject();
        for (ModuleEnum en : ModuleEnum.values()) {
            jsonObject.put(en.getModule(),new JSONArray());
        }
        return jsonObject;
    }

    /**
     * @Description 通过module 获取中文名
     * @Author: weijunjie
     * @Date: 2020/8/18 10:33
     * @return: java.lang.String
     **/
    public static String getName(String desc) {
        for (ModuleEnum en : ModuleEnum.values()) {
            if(en.module.equals(desc)){
                return en.getName();
            }
        }
        return null;
    }

    public static String getRemarks(String desc) {
        String remarks = null;
        switch (desc){
            case "userModule":remarks = "添加、删除、启停、重置用户数据";break;
            case "roleModule":remarks = "添加、删除、分配用户角色";break;
            case "authModule":remarks = "修改、更新角色接口访问权限";break;
            case "dictModule":remarks = "字典查询模块";break;
            default:remarks = "模块作用";break;
        }
        return remarks;
    }

    private String module;

    private String name;

    public String getModule() {
        return module;
    }

    public String getName() {
        return name;
    }

    ModuleEnum() {
    }

    ModuleEnum(String module, String name) {
        this.module = module;
        this.name = name;
    }
}
