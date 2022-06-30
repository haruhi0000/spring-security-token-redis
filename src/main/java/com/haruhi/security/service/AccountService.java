package com.haruhi.security.service;

import com.haruhi.security.dto.AccountDto;
import com.haruhi.security.entity.Account;
import com.haruhi.security.entity.AccountGroup;
import com.haruhi.security.entity.GroupRole;
import com.haruhi.security.entity.Role;
import com.haruhi.security.exception.AccountException;
import com.haruhi.security.repository.AccountGroupRepository;
import com.haruhi.security.repository.AccountRepository;
import com.haruhi.security.repository.GroupRoleRepository;
import com.haruhi.security.repository.RoleRepository;
import com.haruhi.security.security.SessionOnRedisDAO;
import com.haruhi.security.vo.AccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author 61711
 */
@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AccountGroupRepository accountGroupRepository;
    @Autowired
    GroupRoleRepository groupRoleRepository;
    @Autowired
    SessionOnRedisDAO sessionOnRedisDAO;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AccountVo login(String name, String password, HttpServletRequest httpServletRequest) throws AccountException {
        Account account = accountRepository.getByName(name);
        if (account == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        Set<String> roleNames = new HashSet<>();
        if (passwordEncoder.matches(password, account.getPassword())) {
            List<AccountGroup> accountGroups = accountGroupRepository.findByAccountId(account.getId());
            for (AccountGroup accountGroup : accountGroups) {
                List<GroupRole> groupRoles = groupRoleRepository.findByGroupId(accountGroup.getGroupId());
                for (GroupRole groupRole : groupRoles) {
                    Role role = roleRepository.getById(groupRole.getRoleId());
                    if (role != null) {
                        roleNames.add(role.getName());
                    }
                }
            }
            AccountVo accountVo = new AccountVo();
            accountVo.setId(account.getId());
            accountVo.setName(account.getName());
            String token = UUID.randomUUID().toString();
            accountVo.setToken(token);
            accountVo.setRoleNames(roleNames);
            AccountDto accountDto = new AccountDto();
            accountDto.setId(account.getId());
            accountDto.setName(account.getName());
            accountDto.setToken(token);
            accountDto.setRoleNames(roleNames);
            sessionOnRedisDAO.save(accountDto);
            return accountVo;
        } else {
            throw new AccountException("密码错误");
        }
    }

    public void logout(AccountDto accountDto) {
        sessionOnRedisDAO.remove(accountDto.getToken());
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(AccountDto accountDto) {
        Account account = new Account();
        account.setName(accountDto.getName());
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        accountRepository.save(account);
    }
    public Account getByName(String name) {
        return accountRepository.getByName(name);
    }
}
