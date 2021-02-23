package com.wei.pojo;

import java.io.Serializable;

@SuppressWarnings("all")
public class UserInfo extends BaseBean implements Serializable {

    public static final String USER_STATUS_1 = "1";
    public static final String USER_STATUS_0 = "0";
    private Integer id;

    /**
     * 用户账号
     */
    private String accountNo;

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户信息描述
     */
    private String remarks;

    /**
     * 用户状态 0、停用 1、可用
     */
    private String userStatus;

    /**
     * 角色Code
     */
    private String roleCode;

    private static final long serialVersionUID = 1L;

    public UserInfo(Integer id, String accountNo, String userCode, String userName, String password, String remarks, String userStatus, String roleCode) {
        this.id = id;
        this.accountNo = accountNo;
        this.userCode = userCode;
        this.userName = userName;
        this.password = password;
        this.remarks = remarks;
        this.userStatus = userStatus;
        this.roleCode = roleCode;
    }

    public UserInfo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", accountNo=").append(accountNo);
        sb.append(", userCode=").append(userCode);
        sb.append(", userName=").append(userName);
        sb.append(", password=").append(password);
        sb.append(", remarks=").append(remarks);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", roleCode=").append(roleCode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}