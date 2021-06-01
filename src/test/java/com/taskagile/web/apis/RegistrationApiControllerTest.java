package com.taskagile.web.apis;

import com.taskagile.domain.application.UserService;
import com.taskagile.domain.model.user.EmailAddressExistException;
import com.taskagile.domain.model.user.UsernameExistException;
import com.taskagile.utils.JsonUtils;
import com.taskagile.web.payload.RegistrationPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RegistrationApiController.class})
@WebMvcTest
@ActiveProfiles("test")
class RegistrationApiControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("비어있는 페이로드로 register 요청이 들어오면 실패하며, bad request code 를 리턴한다. ")
    void register_blankPayload_shouldBadRequest() throws Exception {
        mockMvc.perform(post("/api/registrations"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("서비스 객체가 EmailAddressExistException을 발생시키면, bad request code 리턴")
    void register_existedEmailAddress_shouldBadRequest() throws Exception{
        RegistrationPayload payload = new RegistrationPayload();
        payload.setUsername("dooon");
        payload.setEmailAddress("existemailaa@gm.com");
        payload.setPassword("1q2w3e4r!!");

        // when
        doThrow(EmailAddressExistException.class)
                .when(userService)
                .register(payload.toCommand());

        // then
        mockMvc.perform(
                post("/api/registrations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtils.toJson(payload)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("서비스 객체가 UsernameExistException을 발생시키면, BadRequest 리턴. ")
    void register_existedUsername_shouldBadRequest() throws Exception {
        RegistrationPayload payload = new RegistrationPayload();
        payload.setUsername("existStub");
        payload.setEmailAddress("hello@gm.com");
        payload.setPassword("1q2w3e4r");

        // when
        // UserService 가 UsernameExistException 을 발생시키면
        doThrow(UsernameExistException.class)
                .when(userService)
                .register(payload.toCommand());
        // then
        // 400 코드 응답을 보내고, 이미 존재하는 사용자라는 메시지를 보낸다.
        mockMvc.perform(
                post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Username already exists"));
    }

    @Test
    @DisplayName("서비스 객체가 예외를 발생시키지 않으면, 성공이다. 201 코드를 리턴")
    public void register_validPayload_shouldSucceedAndReturn201() throws Exception {
        RegistrationPayload payload = new RegistrationPayload();
        payload.setUsername("hello world");
        payload.setEmailAddress("hello12@gm.com");
        payload.setPassword("1q2w3e4r!");

        // when
        doNothing()
                .when(userService)
                .register(payload.toCommand());

        // then
        mockMvc.perform(
                post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtils.toJson(payload)))
        .andExpect(status().isCreated());
    }
}