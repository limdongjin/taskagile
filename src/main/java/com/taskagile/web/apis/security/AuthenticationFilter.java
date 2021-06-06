package com.taskagile.web.apis.security;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.taskagile.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
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
        String contentType = httpServletRequest.getContentType();
        if(!isSupportedContentType(httpServletRequest)){
            throw new InvalidContentTypeException("only support application/json");
        }
        String requestBody = IOUtils.toString(httpServletRequest.getReader());
        LoginPayload loginPayload = JsonUtils.toObject(requestBody, LoginPayload.class);

        if(isInValidLoginRequest(loginPayload)){
            throw new InsufficientAuthenticationException("username or password insufficient");
        }
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        loginPayload.username,
                        loginPayload.password);

        token.setDetails(this.authenticationDetailsSource.buildDetails(httpServletRequest));

        return this.getAuthenticationManager().authenticate(token);
    }

    private boolean isSupportedContentType(@NotNull final HttpServletRequest httpServletRequest) {
        String contentType = httpServletRequest.getContentType();
        return contentType != null && contentType.toLowerCase().contains("application/json");
    }

    @Getter @Setter
    static class LoginPayload {
        String username;
        String password;
    }

    static boolean isInValidLoginRequest(@Nullable final LoginPayload payload){
        if(payload == null) return true;

        return StringUtils.isBlank(payload.username) || StringUtils.isBlank(payload.password);
    }
}
