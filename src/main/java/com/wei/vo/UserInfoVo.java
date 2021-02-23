package com.wei.vo;


import com.wei.pojo.UserInfo;

/**
 * @ClassName UserRoleInfo
 * @Description :
 * @Author weijunjie
 * @Date 2020/8/19 15:31
 */
public class UserInfoVo extends UserInfo {

    private String roleName;

    private String orgCodeStr;

    private String orgNameStr;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOrgCodeStr() {
        return orgCodeStr;
    }

    public void setOrgCodeStr(String orgCodeStr) {
        this.orgCodeStr = orgCodeStr;
    }

    public String getOrgNameStr() {
        return orgNameStr;
    }

    public void setOrgNameStr(String orgNameStr) {
        this.orgNameStr = orgNameStr;
    }

    public UserInfoVo() {
    }

    public UserInfoVo(Integer id, String accountNo, String userCode, String userName, String password, String remarks, String userStatus, String roleCode, String roleName) {
        super(id, accountNo, userCode, userName, password, remarks, userStatus, roleCode);
        this.roleName = roleName;
    }
}
