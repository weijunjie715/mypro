package com.wei.exception;

/**
 * @ClassName HandleDataException
 * @Description : 数据处理异常
 * @Author weijunjie
 * @Date 2020/8/14 18:08
 */
public class HandleDataException extends RuntimeException {
    public HandleDataException(String msg) {
        super(msg);
    }

    public HandleDataException() {
        super("操作处理数据异常");
    }
}
