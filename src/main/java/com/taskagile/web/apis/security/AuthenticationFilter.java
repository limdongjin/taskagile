package com.taskagile.web.apis.security;

import com.taskagile.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public AuthenticationFilter() {
        // POST "/api/authentications" 요청이 오면 AuthenticationFilter 가 처리하도록 지정
        super(new AntPathRequestMatcher("/api/authentications", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        // 인증이 되지않았다면 예외를 던진다., 인증되었다면 Authentication 객체를 리턴한다.

        String requestBody = IOUtils.toString(httpServletRequest.getReader());

        LoginRequest loginRequest = JsonUtils.toObject(requestBody, LoginRequest.class);

        if(isInValidLoginRequest(loginRequest)){
            // 시큐리티 내부에 있는 AuthenticationFailureHandler 가 처리한다.
            throw new InsufficientAuthenticationException("Invalid Authentication Request");
        }

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password);

        return this.getAuthenticationManager().authenticate(token);
    }

    @Getter @Setter
    static class LoginRequest {
        private String username;
        private String password;
    }

    static boolean isInValidLoginRequest(LoginRequest loginRequest){
        if(loginRequest == null) return true;

        return StringUtils.isBlank(loginRequest.username) || StringUtils.isBlank(loginRequest.password);
    }
}
