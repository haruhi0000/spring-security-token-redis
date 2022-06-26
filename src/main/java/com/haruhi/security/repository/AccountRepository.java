package com.haruhi.security.repository;

import com.haruhi.security.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * @author 61711
 */
@Repository
public interface AccountRepository extends CrudRepository<Account,Long> {
    /**
     * 根据name查询
     * @param name name
     * @return Account
     */
    Account getByName(String name);
}
