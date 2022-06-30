package com.haruhi.security.repository;

import com.haruhi.security.entity.AccountTeam;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author 61711
 */
public interface AccountTeamRepository extends CrudRepository<AccountTeam, Long> {
    List<AccountTeam> findByAccountId(Long accountId);
}