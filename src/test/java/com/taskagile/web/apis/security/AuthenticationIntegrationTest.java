package com.taskagile.web.apis.security;

import com.taskagile.utils.JsonUtils;
import com.taskagile.web.payload.RegistrationPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationIntegrationTest {
    @Autowired MockMvc mockMvc;

    @Test
    @DisplayName("회원가입, 로그인 시나리오 테스트")
    @Transactional
    void test() throws Exception {
        final String emailAddress = "hello12@gma.com";
        final String username = "hello world";
        final String password = "1q2w3e4r!!!";

        AuthenticationFilter.LoginPayload loginPayload =
                new AuthenticationFilter.LoginPayload();
        loginPayload.setUsername(emailAddress);
        loginPayload.setPassword(password);

        // 가입되지않은 상태에서 로그인 요청은 실패한다.
        mockMvc.perform(post("/api/authentications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtils.toJson(loginPayload)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("fail"))
        ;

        RegistrationPayload payload = new RegistrationPayload();
        payload.setUsername(username);
        payload.setEmailAddress(emailAddress);
        payload.setPassword(password);

        // 가입 요청
        mockMvc.perform(
                    post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(payload)))
                .andExpect(status().isCreated())
        ;

        // 가입을 했기때문에 로그인 요청은 성공한다.
        mockMvc.perform(post("/api/authentications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(loginPayload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorities").exists())
        ;
    }
}
