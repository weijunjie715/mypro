package com.wei.enums;

/**
 * @ClassName ResultEnum
 * 描述 :
 * @Author weijunjie
 * @Date 2020/7/30 17:43
 */
public enum ResultEnum {
    ADD_SUCCESS(200,"添加成功"),
    ADD_FAIL(205,"添加失败"),

    UPLOAD_SUCCESS(200,"上传成功"),
    UPLOAD_ERROR(9999,"上传发生异常"),

    DEL_SUCCESS(200,"删除成功"),
    DEL_FAIL(205,"删除失败"),

    UP_SUCCESS(200,"更新成功"),
    UP_FAIL(205,"更新失败"),

    SYS_ERROR(999,"系统异常"),

    PARAMS_MISS(405,"参数缺失"),

    DATA_REPETITION(405,"数据重复"),

    DATA_NOT_MATCH(305,"数据不匹配"),

    DATA_TOO_LONG(405,"数据长度超长"),



    SUCCESS(200,"请求成功"),
    LIST_SAVE_ERROR(10,"批量存储出错"),
    LOGIN_SUCCESS(200,"登录成功"),
    LOGOUT_SUCCESS(200,"注销成功"),
    GET_SUCCESS(200,"查询成功"),
    UPDATE_SUCCESS(200,"删除成功"),
    RET_CODE_999(999,"系统异常"),
    RET_CODE_900(900,"运行时系统异常"),
    RET_CODE_403(403,"权限不足"),
    LOGIN_ERROR(999,"账号密码错误，登录失败"),
    RET_CODE_401(401,"权限校验失败，请重新登录"),
    RET_CODE_404(404,"找不到资源"),
    RET_CODE_400(400,"系统异常"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultEnum() {
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
