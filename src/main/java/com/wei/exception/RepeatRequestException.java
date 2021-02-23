package com.wei.exception;

/**
 * @ClassName RepeatRequestException
 * @Description :
 * @Author weijunjie
 * @Date 2020/8/24 17:34
 */
public class RepeatRequestException extends RuntimeException {
    public RepeatRequestException(String msg) {
        super(msg);
    }

    public RepeatRequestException() {
        super();
    }
}
