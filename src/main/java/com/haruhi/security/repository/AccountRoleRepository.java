package com.haruhi.security.repository;

import com.haruhi.security.entity.AccountRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author 61711
 */
@Repository
public interface AccountRoleRepository extends CrudRepository<AccountRole,Long> {

    /**
     * 查询用户的所有角色
     * @param accountId 账户id
     * @return Set<AccountRole>
     */
    Set<AccountRole> findByAccountId(Long accountId);
}
