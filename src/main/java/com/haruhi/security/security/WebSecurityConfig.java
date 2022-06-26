package com.haruhi.security.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * 配置Spring Security
 * @author 61711
 */
@EnableWebSecurity
@ComponentScan({"com.haruhi.security"})
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SessionOnRedisDAO sessionOnRedisDAO;
    @Autowired
    CustomAuthenticationManager customAuthenticationManager;

    /**
     * 不要用bean的方式创建 CustomAuthenticationFilter，
     * 否则WebSecurity.ignoring()方法会没有效果。
     *
     * <p>
     * 因为CustomAuthenticationFilter是身份认证过滤器，所以要添加到ExceptionTranslationFilter之后，
     * 让ExceptionTranslationFilter处理认证失败抛出的异常。
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception - if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and()
                .csrf().disable()
                .headers().frameOptions().sameOrigin().and()
                .addFilterAfter(new CustomAuthenticationFilter(sessionOnRedisDAO, customAuthenticationManager), ExceptionTranslationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler());
    }

    /**
     * 添加Spring Security忽略的请求，主要为静态资源请求，
     * 被添加的请求将无法被Spring Security保护。
     * @param web WebSecurity
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/api/login",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/**",
                "/h2-console/**",
                "/error");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
