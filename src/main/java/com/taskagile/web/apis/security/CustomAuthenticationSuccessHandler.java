package com.taskagile.web.apis.security;

import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.utils.JsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            requestCache.removeRequest(request, response);
            clearAuthenticationAttributes(request);
        }

        SimpleUser securityUser = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                securityUser = (SimpleUser) principal;
            }
        }

//        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//        MediaType jsonMimeType = MediaType.APPLICATION_JSON;

//
//        if (jsonConverter.canWrite(result.getClass(), jsonMimeType)) {
//            jsonConverter.write(result, jsonMimeType, new ServletServerHttpResponse(response));
//        }
        Object result = JsonUtils.toObject(JsonUtils.toJson(securityUser), HashMap.class);
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setStatus(HttpStatus.OK.value());
        JsonUtils.write(response.getWriter(), result);
    }
    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
