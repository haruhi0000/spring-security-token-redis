package com.haruhi.security.controller;

import com.haruhi.security.dto.AccountDto;
import com.haruhi.security.exception.AccountException;
import com.haruhi.security.service.AccountService;
import com.haruhi.security.vo.AccountVo;
import com.haruhi.security.web.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Set;

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
                                      @RequestParam("password") String password) {
        AccountVo accountVo;
        try {
            accountVo = accountService.login(name, password);
            logger.info("login");
        } catch (AccountException e) {
            return ApiResult.failed(e.getMessage());
        }
        return ApiResult.success(accountVo);
    }
    @PutMapping("/api/logout")
    public ApiResult<?> logout(@ApiIgnore @SessionAttribute("account") AccountDto accountDto) {
        accountService.logout(accountDto);
        logger.info("logout");
        return ApiResult.success();
    }
    @PostMapping("/api/account/create")
    public ApiResult<?> newAccount(@RequestParam("name") String name,
                                @RequestParam("password") String password,
                                @RequestParam("roleIds") Set<Long> roleIds) {
        AccountDto accountDto = new AccountDto();
        accountDto.setName(name);
        accountDto.setPassword(password);
        accountService.create(accountDto);
        logger.info("created account");
        return ApiResult.success();
    }
    @GetMapping("/api/account/list")
    public ApiResult<?> list(@ApiIgnore @SessionAttribute("account") AccountDto accountDto) {
        List<AccountVo> accountVos = accountService.list(accountDto);
        return ApiResult.success();
    }
}
