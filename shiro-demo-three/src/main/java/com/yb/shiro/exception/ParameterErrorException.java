package com.yb.shiro.exception;

/**
 * @Author yangbiao
 * @Description:
 * @Date $date$ $time$
 */
public class ParameterErrorException extends Exception{
    /**
     * @Author yangbiao
     * @Description:继承父类的构造
     * @Date 2018/8/5/005 16:31
     */
    public ParameterErrorException(String message) {
        super(message);
    }

    /**
     * @Author yangbiao
     * @Description:填写自定义信息的静态方法
     * @Date 2018/8/5/005 16:35
     */
    public static void messages(String messages){
        ParameterErrorException parameterErrorException = new ParameterErrorException(messages);
    }
}