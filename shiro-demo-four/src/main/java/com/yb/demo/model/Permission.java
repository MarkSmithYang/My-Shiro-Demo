package com.yb.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
@Entity
//@Table
public class Permission implements Serializable {
    private static final long serialVersionUID = 8711506041051913964L;

    @Id
    private String id;

    /**
     * 权限
     */
    private String permission;

    /**
     * 权限描述
     */
    private String permissionDescribe;

    /**
     * 用户的角色
     */
    @ManyToMany(targetEntity = UserInfo.class,fetch = FetchType.LAZY)
    private Set<UserInfo> users = new HashSet<>(0);

    /**
     * 用户的权限
     */
    @ManyToMany(targetEntity = Role.class, mappedBy = "permissions",fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>(0);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermissionDescribe() {
        return permissionDescribe;
    }

    public void setPermissionDescribe(String permissionDescribe) {
        this.permissionDescribe = permissionDescribe;
    }

    public Set<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(Set<UserInfo> users) {
        this.users = users;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
