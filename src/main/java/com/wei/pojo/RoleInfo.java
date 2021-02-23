package com.wei.pojo;

import java.io.Serializable;

@SuppressWarnings("all")
public class RoleInfo extends BaseBean implements Serializable {

    public static final String ROLE_STATUS_1 = "1";
    public static final String ROLE_STATUS_0 = "0";
    private Integer id;

    /**
     * 角色编号
     */
    private String roleCode;

    /**
     * 角色名字
     */
    private String roleName;

    /**
     * 备注
     */
    private String remarks;

    private String roleStatus;

    private static final long serialVersionUID = 1L;

    public RoleInfo(Integer id, String roleCode, String roleName, String remarks) {
        this.id = id;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.remarks = remarks;
    }

    public String getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(String roleStatus) {
        this.roleStatus = roleStatus;
    }

    public RoleInfo() {
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleCode=").append(roleCode);
        sb.append(", roleName=").append(roleName);
        sb.append(", remarks=").append(remarks);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}