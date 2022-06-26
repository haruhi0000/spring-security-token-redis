package com.haruhi.security.security;

import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


/**
 * 负责身份认证的类，实现{@link AuthenticationManager}接口。
 * <p>
 * 该类不做任何处理，只是为了禁用Spring Security的{@link UserDetailsServiceAutoConfiguration}。
 *
 * 如果实现AuthenticationManager，AuthenticationProvider或UserDetailsService中的一个，
 * UserDetailsServiceAutoConfiguration就会被禁用。
 * 该类需要加上@Component注解才会有效。
 * @author 61711
 */
@Component
public class CustomAuthenticationManager implements AuthenticationManager {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authentication;
    }
}
