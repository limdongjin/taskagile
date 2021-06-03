package com.taskagile.web.payload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationPayloadTest {

    private Validator validator;

    private static RegistrationPayload createRegistrationPayload(
            String username,
            String emailAddress,
            String password
    ){
        RegistrationPayload payload = new RegistrationPayload();
        payload.setUsername(username);
        payload.setEmailAddress(emailAddress);
        payload.setPassword(password);

        return payload;
    }

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory()
                            .getValidator();
    }

    @Test
    @DisplayName("비어있는 페이로드는 실패해야한다.")
    public void validate_blankPayload_shouldFail(){
        RegistrationPayload payload = new RegistrationPayload();
        Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
        assertNotEquals(0, violations.size());
    }

    @Test
    @DisplayName("100 자리가 넘는 이메일 주소는 실패해야한다. ")
    public void validate_payloadWithEmailAddressLongerThan100_shouldFail(){
        // given
        String tooLongEmail = "abc".repeat(32) + "@" + "example.com";
        assertTrue(100 < tooLongEmail.length());

        RegistrationPayload payload = createRegistrationPayload(
                                "user123456",
                                          tooLongEmail,
                                 "1q2w3e4r!");

        // when
        Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);

        // then
        assertNotEquals(0, violations.size());
        violations.forEach(violation ->
            assertEquals("emailAddress", violation.getPropertyPath().toString())
        );
    }

    @ParameterizedTest
    @CsvSource({
            "k, user@example.com, 1q2w3e4r",
            "u, dongjin@never.com, mypassword@"
    })
    @DisplayName("사용자 이름 길이가 2보다 작으면 실패해야한다.")
    public void validate_payloadWithUsernameShorterThan2_shouldFail(String username, String emailAddress, String password){
        // given
        assertTrue( username.length() < 2);
        RegistrationPayload payload = createRegistrationPayload(username, emailAddress, password);

        // when
        Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);

        // then
        assertNotEquals(0, violations.size());
        violations.forEach(violation ->
            assertEquals("username", violation.getPropertyPath().toString())
        );
    }

    @Test
    @DisplayName("사용자 이름 길이가 50보다 길다면 실패해야한다. ")
    public void validate_payloadWithUsernameLongerThan50_shouldFail(){
        // given
        String tooLongUsername = "abcde".repeat(10) + "f";
        assertTrue(tooLongUsername.length() > 50);
        RegistrationPayload payload = createRegistrationPayload(
                    tooLongUsername, "dongjin@never.com", "1qsdada");

        // when
        Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);

        // then
        assertNotEquals(0, violations.size());
        violations.forEach(violation ->
          assertEquals("username", violation.getPropertyPath().toString())
        );
    }

    @Test
    @DisplayName("패스워드 길이가 6보다 작으면 실패해야한다. ")
    public void validate_payloadWithPasswordShorterThan6_shouldFail(){
        // given
        String tooShortPassword = "1q2w3";
        RegistrationPayload payload = createRegistrationPayload("dongjin", "exam@exx.com", tooShortPassword);

        // when
        Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);

        // then
        assertNotEquals(0, violations.size());
        violations.forEach(violation ->
            assertEquals("password", violation.getPropertyPath().toString())
        );
    }

    @Test
    @DisplayName("패스워드 길이가 30보다 크면 실패해야한다. ")
    public void validate_payloadWithPasswordLongerThan30_shouldFail(){
        // given
        String tooLongPassword = "1q2".repeat(10)+'!';
        assertTrue(tooLongPassword.length() > 30);
        RegistrationPayload payload = createRegistrationPayload("dongjin", "exam@exam.com", tooLongPassword);

        // when
        Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);

        // then
        assertNotEquals(0, violations.size());
        violations.forEach(violation ->
                assertEquals("password", violation.getPropertyPath().toString())
        );
    }
}