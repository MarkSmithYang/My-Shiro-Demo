package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/27
 */
@Entity
//@Table
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -8823589811656683440L;

    @Id
    private String id;

    /**
     * 用户名
     */
    //这个是让数据库表来判断用户名是否重复,重复则抛出异常,
    // 当然了通过接口查询的方式也是可以的,因为之前都是这么做的,因为方便做Ajax(阿贾克斯)异步校验,当了不做ajax,
    //用这样的方式,不知道好不好抛出执行信息的异常----->还有就是加了这个就不用担心通过用户名取出多个用户信息了
    @Column(unique = true)
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像存储路径(假设用fastdfs上传)
     */
    private String HeadUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 用户的角色
     */
    @ManyToMany(targetEntity = Role.class, mappedBy = "users")
    private Set<String> roles = new HashSet<>(0);

    /**
     * 用户的权限
     */
    @ManyToMany(targetEntity = Permission.class, mappedBy = "users")
    private Set<String> permissions = new HashSet<>(0);

    public UserInfo() {
    }

    public UserInfo(String username, String password) {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
        this.createTime = LocalDateTime.now();
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getHeadUrl() {
        return HeadUrl;
    }

    public void setHeadUrl(String headUrl) {
        HeadUrl = headUrl;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", HeadUrl='" + HeadUrl + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
