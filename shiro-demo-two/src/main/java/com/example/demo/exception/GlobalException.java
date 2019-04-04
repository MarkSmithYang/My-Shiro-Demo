package com.example.demo.exception;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.Result;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
//@Profile(value = {"dev","test"})//设置环境开发和测试
//@ControllerAdvice//这个和restController是一样的,处理结果成了Json的数据格式,省去@ResponseBody
@RestControllerAdvice//统一捕捉Controller层的异常
public class GlobalException {
    public static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    /**
     * 接口单个参数的校验异常捕捉
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
        List<String> list = Lists.newArrayList();
        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        set.forEach(s -> list.add(s.getMessage()));
        return Result.withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage(CollectionUtils.isNotEmpty(list) ? list.toString() : null);
    }

    /**
     * 处理实体类参数校验的异常捕获
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return Result.withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<String> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        return Result.withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParameterErrorException.class)
    public Result<String> parameterErrorExceptionHandler(ParameterErrorException e) {
        return Result.withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        log.info(e.getMessage());
        return Result.withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage("网络异常");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public Result<String> runtimeExceptionHandler(RuntimeException e) {
        log.info(e.getMessage());
        return Result.withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage("网络异常");
    }


}
