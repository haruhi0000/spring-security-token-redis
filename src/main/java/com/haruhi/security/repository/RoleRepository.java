package com.haruhi.security.repository;

import com.haruhi.security.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * @author 61711
 */
@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {
    /**
     * 查询
     * @param id 角色id
     * @return Role
     */
    Role getById(Long id);
}
