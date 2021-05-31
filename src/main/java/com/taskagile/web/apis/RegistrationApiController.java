package com.taskagile.web.apis;

import com.taskagile.web.payload.RegistrationPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class RegistrationApiController {
    @PostMapping("/api/registrations")
    public ResponseEntity register(@Valid @RequestBody RegistrationPayload payload){
        return ResponseEntity.ok(new Object());
    }
}
