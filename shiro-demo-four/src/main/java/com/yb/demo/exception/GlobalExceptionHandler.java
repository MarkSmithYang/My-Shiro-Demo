package com.yb.demo.exception;

import com.yb.demo.common.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.exceptions.TemplateInputException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author yangbiao
 * @Description:controller层的异常捕捉统一处理类
 * @date 2018/9/28
 */
//@Profile(value = {"dev","test"})//设置环境开发和测试
//@ControllerAdvice//这个和restController是一样的,处理结果成了Json的数据格式,省去@ResponseBody
@RestControllerAdvice//统一捕捉Controller层的异常
public class GlobalExceptionHandler {
    public static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 接口单个参数的校验异常捕捉
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
        List<String> list = new ArrayList<>();
        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        set.forEach(s -> list.add(s.getMessage()));
        return Result.withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage(CollectionUtils.isNotEmpty(list) ? list.toString() : null);
    }

    /**
     * 处理实体类参数校验的异常捕获
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMesssage = " ";
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage() + " ";
        }
        log.info(e.getMessage());
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
    @ExceptionHandler(TemplateInputException.class)
    public Result<String> templateInputExceptionHandler(TemplateInputException e) {
        return Result.withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnauthorizedException.class)
    public ModelAndView exceptionHandler(UnauthorizedException e) {
        log.info(e.getMessage());
        ModelAndView modelAndView = new ModelAndView("/noAuth");
        modelAndView.addObject("mess", "我测试成功了");
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e) {
        log.info(e.getMessage());
        return setError(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView runtimeExceptionHandler(RuntimeException e) {
        log.info(e.getMessage());
        return setError(e.getMessage());
    }

    /**
     * 抽取的处理异常信息的方法--->实测--是因为传入的参数不是json对象,而是表单提交导致的
     * MethodArgumentNotValidException进不去,而是BindResult捕获的返回的异常信息不再仅仅是
     * 中文,还有很多不要的信息
     */
    public ModelAndView setError(String errorMessage) {
        String error = "";
        if (StringUtils.isNotBlank(errorMessage)) {
            String[] array = errorMessage.split(" ");
            if (ArrayUtils.isNotEmpty(array)) {
                for (int i = 0; i < array.length; i++) {
                    if (array[i].matches(".*[\u4e00-\u9fa5]{1,}.*")) {
                        error = error + array[i];
                    }
                }
            }
        }
        //实测证明就算check.html没做接口额跳转,还是可以通过ModelAndView的构造跳转过去
        ModelAndView modelAndView = new ModelAndView("/check");
        modelAndView.addObject("msg", StringUtils.isNotBlank(error) ? error : "网络异常");
        return modelAndView;
    }

}
