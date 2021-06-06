package com.taskagile.web.apis.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AuthenticationFilterTest {
    @Mock
    private AuthenticationManager authenticationManagerMock;
    private static final String METHOD = "POST";
    private static final String URL = "/api/authentications";

    private MockHttpServletRequest mockHttpServletRequest;
    private AuthenticationFilter authenticationFilter;

    @BeforeEach
    void setUp() {
        authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManagerMock);
        mockHttpServletRequest = new MockHttpServletRequest(METHOD, URL);
        mockHttpServletRequest.setContentType(MediaType.APPLICATION_JSON.toString());
    }

    @Test
    @DisplayName("요청바디가 비어있으면 예외가 발생한다. ")
    void attemptAuthentication_emptyReqBody_shouldFail() {
        assertDoesNotThrow(()->{authenticationManagerMock.authenticate(null);});
        // given
        assertEquals(-1, mockHttpServletRequest.getContentLength());

        // when then
        assertThrows(InsufficientAuthenticationException.class, () -> {
          authenticationFilter.attemptAuthentication(mockHttpServletRequest, new MockHttpServletResponse());
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"username\":\"requiredField\",\"emailAddress\":\"NotNecessary\",\"password\":\"requiredField\"}",
            "{\"username\":\"dongjin\",\"firstName\":\"dong\",\"password\":\"1q2w3e\"}"
    })
    @DisplayName("requestBody 에 인증에 불필요한 정보가 포함되어있지다면 예외가 발생한다. ")
    void attemptAuthentication_unNecessaryInfo_shouldFail(String requestBody){
        mockHttpServletRequest.setContent(requestBody.getBytes(StandardCharsets.UTF_8));

        assertThrows(InsufficientAuthenticationException.class, () -> {
            authenticationFilter.attemptAuthentication(mockHttpServletRequest, new MockHttpServletResponse());
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"username\":\"requiredField\",\"password\":\"required\"}"
    })
    @DisplayName("정상적인 request body 라면 예외는 발생하지않는다. ")
    void attemptAuthentication_success(String requestBody){
        mockHttpServletRequest.setContent(requestBody.getBytes(StandardCharsets.UTF_8));
        assertDoesNotThrow(() -> {
            Authentication authentication = authenticationFilter.attemptAuthentication(mockHttpServletRequest, new MockHttpServletResponse());
            System.out.println(authentication);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"username\":\"\",\"password\":\"1q2w3e4r!!\"}",
            "{\"username\":\"user1\",\"password\":\"\"}",
    })
    @DisplayName("username 또는 password 가 비어있으면 예외가 발생한다. ")
    void attemptAuthentication_nullField_shouldFail(String requestBody){
        mockHttpServletRequest.setContent(requestBody.getBytes(StandardCharsets.UTF_8));

        assertThrows(InsufficientAuthenticationException.class, () -> {
            authenticationFilter.attemptAuthentication(mockHttpServletRequest, new MockHttpServletResponse());
        });
    }
}