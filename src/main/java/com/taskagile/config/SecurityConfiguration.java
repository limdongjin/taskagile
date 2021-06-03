package com.taskagile.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String[] PUBLIC = new String[]{
      "/error", "/login", "/logout", "/register", "/api/registrations"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 요청 기반 접근 제한
        http
            .authorizeRequests() // ExpressionInterceptorUrlRegistry
                .antMatchers(PUBLIC).permitAll() // PUBLIC 배열에 있는 경로 리스트는 누구나 허용
                .anyRequest().authenticated() // 그 외의 요청은 인증된 사용자만 접근 가능
                .and() // 메서드 호출체인을 http 오브젝트로 복원
            .formLogin()
                .loginPage("/login")
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logged-out")
                .and()
            .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**",
                "/js/**", "/css/**", "/images/**", "/favicon.ico");
    }
}
