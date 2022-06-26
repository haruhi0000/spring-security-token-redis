package com.haruhi.security.controller;

import com.haruhi.security.entity.Account;
import com.haruhi.security.entity.AccountInfo;
import com.haruhi.security.web.ApiResult;
import com.haruhi.security.exception.AccountException;
import com.haruhi.security.service.AccountService;
import com.haruhi.security.vo.AccountVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 61711
 */
@RestController
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    AccountService accountService;
    public AccountController() {
        logger.info("===================created===================");
    }
    @PostMapping("/api/login")
    public ApiResult<AccountVo> login(@RequestParam("name") String name,
                                   @RequestParam("password") String password,
                                   @ApiIgnore HttpServletRequest httpServletRequest) {
        AccountVo accountVo;
        try {
            accountVo = accountService.login(name, password, httpServletRequest);
            logger.info("login");
        } catch (AccountException e) {
            return ApiResult.failed(e.getMessage());
        }
        return ApiResult.success(accountVo);
    }
    @PutMapping("/api/logout")
    public ApiResult<?> logout(@ApiIgnore @SessionAttribute("accountInfo") AccountInfo accountInfo) {
        accountService.logout(accountInfo);
        logger.info("logout");
        return ApiResult.success();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/account")
    public ApiResult<?> newAccount(@RequestParam("name") String name,
                                @RequestParam("password") String password,
                                @RequestParam("roleId") Long roleId) {
        Account account = new Account();
        account.setName(name);
        account.setPassword(password);
        accountService.create(account);
        logger.info("created account");
        return ApiResult.success();
    }
}
