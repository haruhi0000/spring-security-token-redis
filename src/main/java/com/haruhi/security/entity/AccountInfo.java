package com.haruhi.security.entity;

import java.util.Set;

/**
 * @author 61711
 */
public class AccountInfo {
    private Long id;
    private String name;
    private String password;
    private String token;
    private Set<String> roleNames;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(Set<String> roleNames) {
        this.roleNames = roleNames;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", roleNames=" + roleNames +
                '}';
    }
}
