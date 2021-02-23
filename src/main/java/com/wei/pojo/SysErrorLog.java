package com.wei.pojo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("all")
public class SysErrorLog extends BaseBean implements Serializable {
    private Integer id;

    /**
     * 接口地址
     */
    private String errorInterface;

    /**
     * 发生时间
     */
    private Date errorTime;

    /**
     * 错误code
     */
    private String errorCode;

    /**
     * 错误类型
     */
    private String errorType;

    /**
     * 异常
     */
    private String errorException;

    /**
     * 异常描述
     */
    private String errorMsg;

    private static final long serialVersionUID = 1L;

    public SysErrorLog(Integer id, String errorInterface, Date errorTime, String errorCode, String errorType, String errorException, String errorMsg) {
        this.id = id;
        this.errorInterface = errorInterface;
        this.errorTime = errorTime;
        this.errorCode = errorCode;
        this.errorType = errorType;
        this.errorException = errorException;
        this.errorMsg = errorMsg;
    }

    public SysErrorLog() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getErrorInterface() {
        return errorInterface;
    }

    public void setErrorInterface(String errorInterface) {
        this.errorInterface = errorInterface == null ? null : errorInterface.trim();
    }

    public Date getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode == null ? null : errorCode.trim();
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType == null ? null : errorType.trim();
    }

    public String getErrorException() {
        return errorException;
    }

    public void setErrorException(String errorException) {
        this.errorException = errorException == null ? null : errorException.trim();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", errorInterface=").append(errorInterface);
        sb.append(", errorTime=").append(errorTime);
        sb.append(", errorCode=").append(errorCode);
        sb.append(", errorType=").append(errorType);
        sb.append(", errorException=").append(errorException);
        sb.append(", errorMsg=").append(errorMsg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}