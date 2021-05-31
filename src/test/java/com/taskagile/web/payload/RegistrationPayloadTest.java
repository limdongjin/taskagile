package com.taskagile.web.payload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationPayloadTest {
    private Validator validator;

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
        RegistrationPayload payload = new RegistrationPayload();
        String tooLongEmail = "abc".repeat(32) + "@" + "example.com";
        assertTrue(100 < tooLongEmail.length());

        payload.setEmailAddress(tooLongEmail);
        payload.setUsername("user123456");
        payload.setPassword("1q2w3e4r!!!");

        Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);

        assertNotEquals(0, violations.size());
        violations.forEach(violation ->
            assertEquals("emailAddress", violation.getPropertyPath().toString())
        );
    }

    @Test
    @DisplayName("사용자 이름 길이가 2보다 작으면 실패해야한다.")
    public void validate_payloadWithUsernameShorterThan2_shouldFail(){
        RegistrationPayload payload = new RegistrationPayload();
        String tooShortUsername = "a";
        payload.setUsername(tooShortUsername);
        payload.setEmailAddress("normal@example.com");
        payload.setPassword("1q2w3e4r");

        Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
        assertNotEquals(0, violations.size());
        violations.forEach(violation ->
            assertEquals("username", violation.getPropertyPath().toString())
        );
    }

    @Test
    @DisplayName("사용자 이름 길이가 50보다 길다면 실패해야한다. ")
    public void validate_payloadWithUsernameLongerThan50_shouldFail(){
        RegistrationPayload payload = new RegistrationPayload();
        String tooLongUsername = "abcde".repeat(10) + "f";
        assertTrue(tooLongUsername.length() > 50);

        payload.setUsername(tooLongUsername);
        payload.setEmailAddress("exam@exampl.com");
        payload.setPassword("1q2w3e4r!!!");

        Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
        assertNotEquals(0, violations.size());

        violations.forEach(violation ->
          assertEquals("username", violation.getPropertyPath().toString())
        );
    }

    @Test
    @DisplayName("패스워드 길이가 6보다 작으면 실패해야한다. ")
    public void validate_payloadWithPasswordShorterThan6_shouldFail(){
        RegistrationPayload payload = new RegistrationPayload();
        String tooShortPassword = "1q2w3";
        payload.setUsername("dongjin");
        payload.setEmailAddress("exam@exam.com");
        payload.setPassword(tooShortPassword);

        Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
        assertNotEquals(0, violations.size());
        violations.forEach(violation ->
            assertEquals("password", violation.getPropertyPath().toString())
        );
    }

    @Test
    @DisplayName("패스워드 길이가 30보다 크면 실패해야한다. ")
    public void validate_payloadWithPasswordLongerThan30_shouldFail(){
        RegistrationPayload payload = new RegistrationPayload();
        String tooLongPassword = "1q2".repeat(10)+'!';
        payload.setUsername("dongjin");
        payload.setEmailAddress("exam@exam.com");
        payload.setPassword(tooLongPassword);

        Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
        assertNotEquals(0, violations.size());
        violations.forEach(violation ->
                assertEquals("password", violation.getPropertyPath().toString())
        );
    }
}