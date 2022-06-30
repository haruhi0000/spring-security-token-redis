package com.haruhi.security.repository;

import com.haruhi.security.entity.AccountRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author 61711
 */
public interface AccountRoleRepository extends CrudRepository<AccountRole,Long> {
    List<AccountRole> findByAccountId(Long accountId);
}
