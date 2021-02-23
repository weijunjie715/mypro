package com.wei.vo;

/**
 * @ClassName ResultVo
 * 描述 :
 * @Author weijunjie
 * @Date 2020/7/30 17:41
 */
@SuppressWarnings("all")
public class ResultVo<T> {

    /** 状态码 .*/
    private Integer code;

    /** 消息 .*/
    private String msg;

    /** 具体值 .*/
    private T data;

    public ResultVo() {
    }

    public ResultVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
