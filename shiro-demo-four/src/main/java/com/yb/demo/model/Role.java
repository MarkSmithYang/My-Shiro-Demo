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
public class Role implements Serializable {
    private static final long serialVersionUID = -2975877849722398826L;

    @Id
    private String id;

    /**
     * 角色
     */
    private String role;

    /**
     * 角色描述(一般就是中文名)
     */
    private String roleDescribe;

    /**
     * 用户的角色
     */
    @ManyToMany(targetEntity = UserInfo.class,fetch = FetchType.LAZY)
    private Set<UserInfo> users = new HashSet<>(0);

    /**
     * 用户的权限
     */
    @ManyToMany(targetEntity = Permission.class,fetch = FetchType.EAGER)
    private Set<Permission> permissions = new HashSet<>(0);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleDescribe() {
        return roleDescribe;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }

    public Set<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(Set<UserInfo> users) {
        this.users = users;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
