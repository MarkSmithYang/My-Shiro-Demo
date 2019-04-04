package com.example.demo.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author yangbiao
 * @Description: 注册用户提交信息的封装类
 * @date 2018/9/28
 */
public class RegisterRequest {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Length(max = 20, message = "用户名不能超过20字")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 3, max = 16, message = "密码只能是3到16位的长度")
    private String password;

    /**
     * 再次输入的密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 3, max = 16, message = "密码只能是3到16位的长度")
    private String rePassword;

    /**
     * 校验密码和再次输入密码是否一致
     */
    public Boolean checkPassword() {
        //因为做了非空校验,不可能为空了
        if (this.password.equals(this.rePassword)) {
            return true;
        } else {
            return false;
        }
    }

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

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
