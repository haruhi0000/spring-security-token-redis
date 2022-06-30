package com.haruhi.security.repository;

import com.haruhi.security.entity.GroupRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author 61711
 */
public interface GroupRoleRepository extends CrudRepository<GroupRole,Long> {
    List<GroupRole> findByGroupId(Long groupId);
}
