package com.example.demo.exception;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
public class ParameterErrorException extends RuntimeException {

    public ParameterErrorException(String message) {
        super(message);
    }

    public static void messages(String msg) {
        throw new ParameterErrorException(msg);
    }

}
