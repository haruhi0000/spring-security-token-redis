package com.haruhi.security.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author 61711
 */
@Entity
public class AccountRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_id")
    private Long accountId;
    @Column(name = "role_id")
    private Long roleId;


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountRole that = (AccountRole) o;

        if (!Objects.equals(id, that.id)) {
            return false;
        }
        if (!Objects.equals(accountId, that.accountId)) {
            return false;
        }
        return Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }
}
