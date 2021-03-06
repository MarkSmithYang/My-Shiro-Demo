package com.yb.demo.common;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
public class Result<T> {
    private static final String MESSAGE_SUCCESS = "success";
    private static final Integer STATUS_SUCCESS = 200;
    private static final Integer STATUS_ERROR = 400;

    private Integer status;
    private String message;
    private T data;

    /**
     * 供接口返回数据使用
     */
    public static <T> Result<T> success(T t) {
        Result<T> result = new Result<T>();
        result.setStatus(STATUS_SUCCESS);
        result.setMessage(MESSAGE_SUCCESS);
        result.setData(t);
        return result;
    }

    /**
     * 这个一般用不着,因为基本都是用异常来捕捉的
     */
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<T>();
        result.setStatus(STATUS_ERROR);
        result.setMessage(message);
        return result;
    }

    /**
     * 这个主要用来在异常捕捉类里简化返回数据的封装
     */
    public static Result withStatus(Integer status) {
        Result result = new Result();
        result.setStatus(status);
        return result;
    }

    /**
     * 这个也是主要用来在异常捕捉类里简化返回数据的封装
     */
    public Result withMessage(String message) {
        this.setMessage(message);
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
