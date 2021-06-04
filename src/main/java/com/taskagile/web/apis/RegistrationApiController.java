package com.taskagile.web.apis;

import com.taskagile.domain.application.UserService;
import com.taskagile.domain.model.user.RegistrationException;
import com.taskagile.domain.model.user.UsernameExistException;
import com.taskagile.web.payload.RegistrationPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
public class RegistrationApiController {
    private UserService userService;

    public RegistrationApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/welcome")
    public ResponseEntity welcome(){
        return ResponseEntity.ok("welcome");
    }

    @GetMapping("/api/login-fail")
    public ResponseEntity loginFail(){
        return ResponseEntity.status(401).body("login fail");
    }

    @PostMapping("/api/registrations")
    public ResponseEntity register(
            @Valid @RequestBody RegistrationPayload payload
    ){
        try {
            userService.register(payload.toCommand());
        }catch (RegistrationException e){
            // [TODO] interceptor 가 예외처리 하도록 변경
            String errorMessage = "Registration Failed.";
            if (e instanceof UsernameExistException){
                errorMessage = "Username already exists";
            }

            // [TODO] result 객체 생성 책임을 다른 객체에게 위임하기.
            HashMap<String, Object> result                                                                                                                                                                                                                                                    = new HashMap<>();
            result.put("message", errorMessage);
            //

            return ResponseEntity.badRequest().body(result);
        }
        // [TODO] 가입 완료 결과 dto 만들기
        // RegistrationRes res = new ...
        // return new resentity.body(res)
        return ResponseEntity.status(201).body(payload);
    }
}
