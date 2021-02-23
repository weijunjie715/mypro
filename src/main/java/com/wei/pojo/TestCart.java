/*
* TestCart.java
* Copyright(C) 2020-2025 myPro
* @date 2021-02-23
*/
package com.wei.pojo;

import java.io.Serializable;

@SuppressWarnings("all")
public class TestCart extends BaseBean implements Serializable {
    private Integer id;

    private String orderid;

    private String iccid;

    private String usercode;

    private Integer timetype;

    private Integer types;

    private static final long serialVersionUID = 1L;

    public TestCart(Integer id, String orderid, String iccid, String usercode, Integer timetype, Integer types) {
        this.id = id;
        this.orderid = orderid;
        this.iccid = iccid;
        this.usercode = usercode;
        this.timetype = timetype;
        this.types = types;
    }

    public TestCart() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    public Integer getTimetype() {
        return timetype;
    }

    public void setTimetype(Integer timetype) {
        this.timetype = timetype;
    }

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderid=").append(orderid);
        sb.append(", iccid=").append(iccid);
        sb.append(", usercode=").append(usercode);
        sb.append(", timetype=").append(timetype);
        sb.append(", types=").append(types);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}