package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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
    @ManyToMany(targetEntity = UserInfo.class)
    private Set<String> users = new HashSet<>(0);

    /**
     * 用户的权限
     */
    @ManyToMany(targetEntity = Permission.class)
    private Set<String> permissions = new HashSet<>(0);

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

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
