package com.haruhi.security.security;

import com.haruhi.security.dto.AccountDto;
import com.haruhi.security.entity.AccountInfo;
import com.haruhi.security.exception.AccountSessionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 基于redis的身份认证拦截器，
 * redis中保存着已经登录的所有会话，
 * 通过请求头中的token字段获取redis中的会话信息，
 * 如果获取会话成功就把用户的信息和权限信息保存到SecurityContext和HttpSession中，
 * 否则抛出AuthenticationException异常。
 *
 * 不要用bean的方式创建该类的对象，因为如果创建为Bean，Spring Boot会扫描到并注册到Tomcat容器的默认过滤器链中。
 *
 * @author 61711
 */

public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    private final SessionOnRedisDAO sessionOnRedisDAO;
    private final AuthenticationManager authenticationManager;
    public CustomAuthenticationFilter(SessionOnRedisDAO sessionOnRedisDAO, AuthenticationManager authenticationManager) {
        this.sessionOnRedisDAO = sessionOnRedisDAO;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        logger.info("CustomAuthenticationFilter");
        String token = request.getHeader("token");

        if (token == null) {
            throw new BadCredentialsException("没有token");
        }
        AccountDto accountDto;
        try {
            accountDto = sessionOnRedisDAO.get(token);
        } catch (AccountSessionNotFoundException e) {
            throw new BadCredentialsException(e.getMessage());
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (String roleName : accountDto.getRoleNames()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(roleName));
        }

        Authentication customAuthentication = new CustomAuthentication(accountDto, token, grantedAuthorities);
        customAuthentication = authenticationManager.authenticate(customAuthentication);
        SecurityContextHolder.getContext().setAuthentication(customAuthentication);
        request.getSession().setAttribute("account", accountDto);
        filterChain.doFilter(request, response);
    }

}
