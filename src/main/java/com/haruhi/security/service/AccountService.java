package com.haruhi.security.service;

import com.haruhi.security.dto.AccountDto;
import com.haruhi.security.entity.*;
import com.haruhi.security.exception.AccountException;
import com.haruhi.security.repository.*;
import com.haruhi.security.security.SessionOnRedisDAO;
import com.haruhi.security.vo.AccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    AccountTeamRepository accountTeamRepository;
    @Autowired
    AccountRoleRepository accountRoleRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    SessionOnRedisDAO sessionOnRedisDAO;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AccountVo login(String name, String password) throws AccountException {
        Account account = accountRepository.getByName(name);
        if (account == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        Set<String> roleNames = new HashSet<>();
        if (passwordEncoder.matches(password, account.getPassword())) {
            List<AccountTeam> accountTeams = accountTeamRepository.findByAccountId(account.getId());
            Set<Team> teams = new HashSet<>();
            for (AccountTeam accountTeam : accountTeams) {
                Optional<Team> optionalGroup = teamRepository.findById(accountTeam.getTeamId());
                optionalGroup.ifPresent(teams::add);
            }
            List<AccountRole> accountRoles = accountRoleRepository.findByAccountId(account.getId());
            Set<Role> roles = new HashSet<>();
            for (AccountRole accountRole : accountRoles) {
                Optional<Role> optionalRole = roleRepository.findById(accountRole.getRoleId());
                optionalRole.ifPresent(role -> {
                    roles.add(role);
                    roleNames.add(role.getName());
                });
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
            accountDto.setRoles(roles);
            accountDto.setTeams(teams);
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

    public List<AccountVo> list(AccountDto accountDto) {

        return null;
    }
}
