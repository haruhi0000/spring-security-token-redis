package com.haruhi.security.repository;

import com.haruhi.security.entity.AccountGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author 61711
 */
public interface AccountGroupRepository extends CrudRepository<AccountGroup, Long> {
    List<AccountGroup> findByAccountId(Long accountId);
}