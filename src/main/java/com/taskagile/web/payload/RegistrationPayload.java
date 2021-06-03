package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.RegisterCommand;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class RegistrationPayload {
    // 생성자 메서드는 작성하지 않았다. Payload 는 스프링이 setter 로 필드 값을 채워넣기때문에 생성자가
    // 필요 없다고 판단하였다.
    @Size(min = 2, max = 50, message = "Username must be 2  <= _ <= 50 characters")
    @NotNull
    private String username;

    @Email(message = "Email address should be valid")
    @Size(max = 100, message = "Email address must not be more than 100 characters")
    @NotNull
    private String emailAddress;

    @Size(min = 6, max = 30, message = "Password must be 6 <= _ <= 30 characters")
    @NotNull
    private String password;

    public RegisterCommand toCommand() {
        return new RegisterCommand(username, emailAddress, password);
    }
}
