package com.yb.shiro.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * Author yangbiao
 * Description:接收信息用户登录表单的实体
 * @Date 2018/9/11/011 21:54
 */
public class UserRequest {

    private String id;

    @NotBlank(message = "用户名不能为空")
    @Length(max = 50, message = "用户名太长")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(max = 16, message = "密码不能超过16位")
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
//        JSONObject jsonObject = new JSONObject();
//        Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
//        entries.forEach(s-> System.err.println(s.getKey()+"--"+s.getValue()));
    }
}