package com.taskagile.web.apis;

import com.taskagile.web.payload.RegistrationPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(RegistrationApiController.class)
@ExtendWith(SpringExtension.class)
class RegistrationApiControllerTest {
    @Autowired private MockMvc mockMvc;

    @Test
    @DisplayName("비어있는 페이로드로 register 요청이 들어오면 실패하며, 400 코드를 리턴한다. ")
    void register_blankPayload_shouldFailAndReturn400() throws Exception {
        mockMvc.perform(post("/api/registrations"))
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("이미 존재하는 사용자 이름으로 가입요청이 들어오면 실패한다. 그리고 400 코드 리턴. ")
    void register_existedUsername_shouldFailAndReturn400(){
        RegistrationPayload payload = new RegistrationPayload();
        payload.setUsername("existStub");
        payload.setEmailAddress("hello@gm.com");
        payload.setPassword("1q2w3e4r");

        // [TODO] service 객체 목킹
        // 검증 코드 작성하기 .
    }
}