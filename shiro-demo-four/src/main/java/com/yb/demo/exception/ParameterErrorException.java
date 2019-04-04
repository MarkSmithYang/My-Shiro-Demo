package com.yb.demo.exception;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
public class ParameterErrorException extends RuntimeException {

    /**
     * 重写父类的构造(并调用父类构造把参数传给父类)
     * @param message
     */
    public ParameterErrorException(String message) {
        super(message);
    }

    public static void messages(String msg) {
        throw new ParameterErrorException(msg);
    }

}
