package com.taskagile.config;

import com.taskagile.web.apis.authenticate.AuthenticationFilter;
import com.taskagile.web.apis.authenticate.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String[] PUBLIC = new String[]{
      "/error", "/login", "/logout", "/register", "/api/registrations", "/api/login-fail"
    };

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 요청 기반 접근 제한
        http
            .authorizeRequests() // ExpressionInterceptorUrlRegistry
                .antMatchers(PUBLIC).permitAll() // PUBLIC 배열에 있는 경로 리스트는 누구나 허용
                .anyRequest().authenticated() // 그 외의 요청은 인증된 사용자만 접근 가능
            .and() // 메서드 호출체인을 http 오브젝트로 복원
                .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/login")
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
            .and()
                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**",
                "/js/**", "/css/**", "/images/**", "/favicon.ico");
    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());

        return authenticationFilter;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        // 로그인 성공후 핸들링하는 객체
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        // [TODO] 로그인 실패후 json 응답 커스터마이징하기

        SimpleUrlAuthenticationFailureHandler handler
                = new SimpleUrlAuthenticationFailureHandler();
        handler.setDefaultFailureUrl("/api/login-fail");
        return handler;
    }
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new SimpleUrlLogoutSuccessHandler();
    }
}
