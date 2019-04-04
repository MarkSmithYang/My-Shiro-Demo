package com.yb.shiro.exception;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author yangbiao
 * @Description:
 * @Date $date$ $time$
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {
    public static final Logger log = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    /**
     * @Author yangbiao
     * @Description:全局异常处理
     * @Date 2018/8/5/005 16:11
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JSONObject exceptionHandler(Exception e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", HttpStatus.BAD_REQUEST.value());
        log.info(e.getMessage());
//        jsonObject.put("messages", "网络异常");
        jsonObject.put("messages", e.getMessage());
        return jsonObject;
    }

    /**
     * @Author yangbiao
     * @Description:运行时异常捕获处理
     * @Date 2018/8/5/005 16:15
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JSONObject runtimeExceptionHandler(RuntimeException e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", HttpStatus.BAD_REQUEST.value());
        log.info(e.getMessage());
//        jsonObject.put("messages", "网络错误");
        jsonObject.put("messages", e.getMessage());
        return jsonObject;
    }

    /**
     * @Author yangbiao
     * @Description:这个常用于抛出自定义的异常信息
     * @Date 2018/8/5/005 16:17
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JSONObject parameterErrorExceptionHandler(ParameterErrorException e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", HttpStatus.BAD_REQUEST.value());
        jsonObject.put("messages", e.getMessage());
        return jsonObject;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JSONObject methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", HttpStatus.BAD_REQUEST.value());
        jsonObject.put("messages", e.getMessage());
        return jsonObject;
    }


}