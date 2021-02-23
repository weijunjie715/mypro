package com.wei.exception;

/**
 * @Description 访问参数异常
 **/
public class QueryParamException extends RuntimeException {
    public QueryParamException(String msg) {
        super(msg);
    }

    public QueryParamException() {
        super();
    }
}
