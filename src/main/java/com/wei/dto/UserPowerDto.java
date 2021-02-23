package com.wei.dto;

import java.util.List;

/**
 * @ClassName UserPowerDto
 * @Description :
 * @Author weijunjie
 * @Date 2020/8/12 13:27
 */
public class UserPowerDto {

    private String userCode;

    private List<String> userPowerList;

    public UserPowerDto() {
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public List<String> getUserPowerList() {
        return userPowerList;
    }

    public void setUserPowerList(List<String> userPowerList) {
        this.userPowerList = userPowerList;
    }
}
