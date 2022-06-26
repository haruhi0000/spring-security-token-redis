package com.haruhi.security.service;

import com.haruhi.security.exception.AccountException;
import com.haruhi.security.security.SessionOnRedisDAO;
import com.haruhi.security.entity.Account;
import com.haruhi.security.entity.AccountInfo;
import com.haruhi.security.entity.AccountRole;
import com.haruhi.security.entity.Role;
import com.haruhi.security.repository.AccountRepository;
import com.haruhi.security.repository.AccountRoleRepository;
import com.haruhi.security.repository.RoleRepository;
import com.haruhi.security.vo.AccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
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
    AccountRoleRepository accountRoleRepository;
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
            Set<AccountRole> accountRoles = accountRoleRepository.findByAccountId(account.getId());
            for (AccountRole accountRole : accountRoles) {
                Role role = roleRepository.getById(accountRole.getRoleId());
                if (role != null) {
                    roleNames.add(role.getName());
                }
            }
            AccountVo accountVo = new AccountVo();
            accountVo.setId(account.getId());
            accountVo.setName(account.getName());
            String token = UUID.randomUUID().toString();
            accountVo.setToken(token);
            accountVo.setRoleNames(roleNames);
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setId(account.getId());
            accountInfo.setName(account.getName());
            accountInfo.setToken(token);
            accountInfo.setRoleNames(roleNames);
            sessionOnRedisDAO.save(accountInfo);
            return accountVo;
        } else {
            throw new AccountException("密码错误");
        }
    }

    public void logout(AccountInfo accountInfo) {
        sessionOnRedisDAO.remove(accountInfo.getToken());
    }

    public void create(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
    }
    public Account getByName(String name) {
        return accountRepository.getByName(name);
    }
}
