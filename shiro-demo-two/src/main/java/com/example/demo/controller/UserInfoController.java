package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.exception.ParameterErrorException;
import com.example.demo.model.UserInfo;
import com.example.demo.request.RegisterRequest;
import com.example.demo.request.UserInfoRequest;
import com.example.demo.service.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.RedirectView;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
@Controller
@CrossOrigin
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/logout")
    public String logout() {
        //清除用户登录信息
        SecurityUtils.getSubject().logout();
        return "/login";
    }

    @GetMapping("/success")
    public String success() {
        return "/success";
    }

    @GetMapping("/noAuth")
    public String noAuth() {
        return "/noAuth";
    }

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

    @GetMapping("/register")
    public String register() {
        return "/register";
    }

    @PostMapping("/userLogin")
    public String userLogin(@Valid UserInfoRequest userInfoRequest,Model model) {
        //System.err.println(request.getParameter("username"));//这里的字符串是和表单的name一样的
        //System.err.println(request.getParameter("password"));//这个也能取到信息,但是不能一起校验
       //把用户名密码放到shiro的token里
        UsernamePasswordToken token = new UsernamePasswordToken(userInfoRequest.getUsername(), userInfoRequest.getPassword());
        //获取Subject
        Subject subject = SecurityUtils.getSubject();
        //通过shiro去登陆
        try {
            subject.login(token);
            //登陆成功
            model.addAttribute("username",userInfoRequest.getUsername());
            return "/index";
        } catch (AuthenticationException e) {
           //登陆失败(设置失败信息)
            model.addAttribute("msg", "用户名或密码错误");
            return "/login";
        }

    }

    /**
     * 通过用户名获取用户信息
     *
     * @param username
     * @return
     */
    @GetMapping("/findByUsername")
    @ResponseBody
    @Validated//单参数校验起效的注解
    public Result<UserInfo> findByUsername(@NotBlank(message = "用户名不能为空")
                                           @Length(max = 20, message = "用户名过长")
                                           @RequestParam String username) {
        UserInfo result = userInfoService.findByUsername(username);
        return Result.success(result);
    }

    /**
     * 用户注册
     */
    @PostMapping("/registerUser")
    public String registerUser(@Valid RegisterRequest registerRequest,Model model) {
        //校验密码与再次数据密码是否重复
        if (!registerRequest.checkPassword()) {
            ParameterErrorException.messages("密码不一致");
        }
        String result = userInfoService.registerUser(registerRequest);
        model.addAttribute("result", result);
        return "/success";
    }

}
