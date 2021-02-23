package com.wei.dto;


import com.wei.pojo.UserInfo;

public class UserInfoDto extends UserInfo {
    private String[] orgCodeList;

    public String[] getOrgCodeList() {
        return orgCodeList.clone();
    }

    public void setOrgCodeList(String[] orgCodeList) {
        this.orgCodeList = orgCodeList.clone();
    }
}
