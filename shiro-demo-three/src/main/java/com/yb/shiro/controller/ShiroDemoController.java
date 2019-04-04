package com.yb.shiro.controller;

import com.yb.shiro.exception.ParameterErrorException;
import com.yb.shiro.model.RegeristerRequest;
import com.yb.shiro.service.ShiroDemoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@CrossOrigin//处理浏览器跨域的
public class ShiroDemoController {
    @Autowired
    private ShiroDemoService shiroDemoService;

    public static final String code="cqaq";

    /**
     * @Author yangbiao
     * @Description:注册接口
     * @Date 2018/8/5/005 15:37
     */
    @PostMapping("/regerister")
    public String regerister(@Validated RegeristerRequest regeristerRequest, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            if(errors!=null && !errors.isEmpty()){
            model.addAttribute("msg",errors.get(0).getDefaultMessage());
            }
//            errors.forEach(s->model.addAttribute("msg",s.getDefaultMessage()));
        }
        //获取注册表单提交的内容
        String username = regeristerRequest.getUsername();
        String password = regeristerRequest.getPassword();
        String checkCode = regeristerRequest.getCheckCode();
        //判断验证码是否正确

        if(StringUtils.isNotBlank(checkCode) && code.equals(checkCode.toLowerCase())){
           if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
               //保存用户信息
               shiroDemoService.save(username,password);
           }
        }else {
            ParameterErrorException.messages("验证码不正确");
        }
        //注册成功跳转到登录页
        return "login";
    }

    /**
     * @Author yangbiao
     * @Description:通过shiro进行登录认证
     * @Date 2018/8/5/005 15:35
     */
    @GetMapping("/loginUser")
    public String loginUser(Model model, HttpServletRequest request, BindingResult bindingResult){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //封装用户名和密码
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
       //执行登录
        try {
            //执行登录
            subject.login(token);
            //登录成功,跳转到主页
            System.err.println("登录陈宫?");
            return "redirect:/index";
        } catch (Exception e) {
           model.addAttribute("msg","用户名或密码错误");
           return "login";
        }
    }

    /**
     * @Author yangbiao
     * @Description:首页
     * @Date 2018/8/5/005 15:35
     */
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * @Author yangbiao
     * @Description:跳到登录页
     * @Date 2018/8/5/005 15:35
     */
    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @GetMapping("/noAuth")
    public String noAuth(){
        return "/noAuth";
    }

    @GetMapping("/reger")
    public String reger(){
        return "/regerister";
    }


}