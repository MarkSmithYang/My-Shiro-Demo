package com.yb.shiro.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Author yangbiao
 * @Description:接收信息用户注册表单的实体
 * @Date $date$ $time$
 */
public class RegeristerRequest {

    @NotBlank(message = "用户名不能为空")
    @Length(max = 50,message = "用户名太长")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(max = 16,message = "密码不能超过16位")
    private String password;

    @NotBlank(message = "验码不能为空")
    @Length(min = 4,max = 6,message = "验码错误")
    private String checkCode;

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
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

}