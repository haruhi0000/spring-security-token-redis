package com.haruhi.security.controller;

import com.haruhi.security.web.ApiResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 61711
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping("/hasRoleUserOrAdmin")
    public ApiResult<?> hasRoleUserOrAdmin() {
        return ApiResult.success();
    }
    @PreAuthorize("hasRole('USER') AND hasRole('ADMIN')")
    @GetMapping("/hasRoleUserAndAdmin")
    public ApiResult<?> hasRoleUserAndAdmin() {
        return ApiResult.success();
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hasRoleUser")
    public ApiResult<?> hasRoleUser() {
        return ApiResult.success();
    }
}
