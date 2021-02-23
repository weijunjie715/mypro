package com.wei.exception;

/**
 * @Description 访问权限异常
 **/
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}
