package com.yb.demo.controller;

import com.yb.demo.common.CommonDic;
import com.yb.demo.request.RegisterRequest;
import com.yb.demo.request.UserInfoRequest;
import com.yb.demo.service.UserInfoService;
import com.yb.demo.utils.RealIpGetUtils;
import com.yb.demo.utils.VerifyCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
@Controller
@CrossOrigin
public class UserInfoController {
    public static final Logger log = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;
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

    @RequiresPermissions(value = {"all", "any"}, logical = Logical.OR)
    @GetMapping("/website")
    public String website() {
        return "/website";
    }

    //@RequiresRoles(value = {"admin"})
    @RequiresPermissions({"all"})
    @GetMapping("/noAuth")
    public String noAuth() {
        return "/noAuth";
    }

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "/index";
    }

    @GetMapping("/register")
    public String register() {
        return "/register";
    }

    /**
     * 用户登录--->这里参数的校验信息在里没法放在模型里返回给前端,只能按平时的那样返回
     */
    @PostMapping("/userLogin")
    public String userLogin(@Valid UserInfoRequest userInfoRequest, Model model, HttpServletRequest request) {
        //System.err.println(request.getParameter("username"));//这里的字符串是和表单的name一样的
        //System.err.println(request.getParameter("password"));//这个也能取到信息,但是不能一起校验
        //把用户名密码放到shiro的token里
        String username = userInfoRequest.getUsername();
        String password = userInfoRequest.getPassword();
        //获取服务的真实地址
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);
        //获取Subject
        Subject subject = SecurityUtils.getSubject();
        //通过shiro去登陆
        try {
            subject.login(token);
            //登陆成功
            model.addAttribute("username", userInfoRequest.getUsername());
            return "/index";
        } catch (AuthenticationException e) {
            //登陆失败(设置失败信息)
            model.addAttribute("msg", "用户名或密码错误");
            return "/login";
        }
    }

    /**
     * 用户注册--->这里参数的校验信息在里没法放在模型里返回给前端,只能按平时的那样返回
     */
    @PostMapping("/registerUser")
    public String registerUser(@Valid RegisterRequest registerRequest, Model model, HttpServletRequest request) {
        //校验密码与再次数据密码是否重复
        if (!registerRequest.checkPassword()) {
            model.addAttribute("msg", "两次输入密码不一致");
            return "/register";
        }
        //注册
        String result = userInfoService.registerUser(registerRequest);
        model.addAttribute("message", result);
        //返回
        if ("注册成功".equals(result)) {
            return "/login";
        }
        return "/register";
    }

    @GetMapping("/verifyCodeCheck")
    @ResponseBody
    public String verifyCodeCheck(String verifyCode, HttpServletRequest request) {
        if (StringUtils.isNotBlank(verifyCode)) {
            //获取服务ip
            String ipAddress = RealIpGetUtils.getIpAddress(request);
            String key = CommonDic.VERIFYCODE_SIGN_PRE + ipAddress;
            //获取redis上的存储的(最新的)验证码
            String code = (String) redisTemplate.opsForValue().get(key);
            //校验验证码
            if (StringUtils.isNotBlank(code) && code.contains("@&")) {
                code = code.split("@&")[1];
                if (verifyCode.toLowerCase().equals(code.toLowerCase())) {
                    return "true";
                }
            } else {
                return "expir";
            }
        }
        return "false";
    }

    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletResponse response, HttpServletRequest request, Model model) {
        Integer times;
        //获取服务ip
        String ipAddress = RealIpGetUtils.getIpAddress(request);
        //拼接存储redis的key
        String key = CommonDic.VERIFYCODE_SIGN_PRE + ipAddress;
        //获取验证码及其刷新次数信息
        String code = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(code) && code.contains("@&")) {
            times = Integer.valueOf(code.split("@&")[0]);
            //判断刷新次数
            if (times > CommonDic.REQUEST_MAX_TIMES) {
                //结束程序--等待redis上的数据过期再重新再来
                return;
            }
            //增加次数
            times++;
        } else {
            times = 0;
        }
        //获取字符验证码
        String verifyCode = VerifyCodeUtils.generateVerifyCode(CommonDic.VERIFYCODE_AMOUNT);
        try {
            VerifyCodeUtils.outputImage(80, 30, response.getOutputStream(), verifyCode);
            //存储验证码并设置过期时间为5分钟--限制点击的次数,防止恶意点击
            redisTemplate.opsForValue().set(key, times + "@&" + verifyCode, CommonDic.VERIFYCODE_EXPIRED, TimeUnit.SECONDS);
        } catch (IOException e) {
            log.info("验证码输出异常");
            e.printStackTrace();
        }
    }
}
