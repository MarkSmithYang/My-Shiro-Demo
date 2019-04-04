package com.yb.demo.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author yangbiao
 * @Description: 注册用户提交信息的封装类
 * @date 2018/9/28
 */
public class UserInfoRequest {

    /**
     * 用户名
     */
    @NotBlank(message = "登录用户名不能为空")
    @Length(max = 20, message = "登录用户名不能超过20字")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "登录密码不能为空")
    @Length(min = 3, max = 16, message = "登录密码只能是3到16位的长度")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
