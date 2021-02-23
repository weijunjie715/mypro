package com.wei.vo;

import com.alibaba.fastjson.JSONArray;

/**
 * @ClassName RolePowerVo
 * @Description :
 * @Author weijunjie
 * @Date 2020/8/20 15:14
 */
public class RolePowerVo {

    private String roleName;

    private String powerName;

    private String powerRemarks;

    private String module;

    private JSONArray actions;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getPowerRemarks() {
        return powerRemarks;
    }

    public void setPowerRemarks(String powerRemarks) {
        this.powerRemarks = powerRemarks;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public RolePowerVo() {
    }

    public JSONArray getActions() {
        return actions;
    }

    public void setActions(JSONArray actions) {
        this.actions = actions;
    }

    public RolePowerVo(String roleName, String powerName, String powerRemarks, String module, JSONArray actions) {
        this.roleName = roleName;
        this.powerName = powerName;
        this.powerRemarks = powerRemarks;
        this.module = module;
        this.actions = actions;
    }
}
