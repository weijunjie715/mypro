package com.wei.pojo;

import java.io.Serializable;

@SuppressWarnings("all")
public class RolePower extends BaseBean implements Serializable {

    public static final String POWER_TYPE_TYPE_1 = "1";
    public static final String POWER_TYPE_TYPE_2 = "2";

    private Integer id;

    /**
     * 角色code
     */
    private String roleCode;

    /**
     * 权限类型 1、按钮权限  2、模块权限
     */
    private String powerType;

    /**
     * 权限名
     */
    private String powerName;

    /**
     * 权限模块
     */
    private String powerModule;

    /**
     * 权限访问
     */
    private String powerAction;

    private String remarks;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    private static final long serialVersionUID = 1L;

    public RolePower(Integer id, String roleCode, String powerType, String powerName, String powerModule, String powerAction) {
        this.id = id;
        this.roleCode = roleCode;
        this.powerType = powerType;
        this.powerName = powerName;
        this.powerModule = powerModule;
        this.powerAction = powerAction;
    }

    public RolePower() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType == null ? null : powerType.trim();
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName == null ? null : powerName.trim();
    }

    public String getPowerModule() {
        return powerModule;
    }

    public void setPowerModule(String powerModule) {
        this.powerModule = powerModule == null ? null : powerModule.trim();
    }

    public String getPowerAction() {
        return powerAction;
    }

    public void setPowerAction(String powerAction) {
        this.powerAction = powerAction == null ? null : powerAction.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleCode=").append(roleCode);
        sb.append(", powerType=").append(powerType);
        sb.append(", powerName=").append(powerName);
        sb.append(", powerModule=").append(powerModule);
        sb.append(", powerAction=").append(powerAction);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}