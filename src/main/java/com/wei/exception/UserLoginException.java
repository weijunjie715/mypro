package com.wei.exception;

/**
 * @Description 用户登录异常
 **/
public class UserLoginException extends RuntimeException {
    public UserLoginException(String msg) {
        super(msg);
    }

    public UserLoginException() {
        super();
    }
}
