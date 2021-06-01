package com.taskagile.domain.common.security;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptorDelegator implements PasswordEncryptor {
    // [TODO] 패스워드 암호화 구현하기 , spring security 패키지의 PasswordEncoder 적용
    //    private PasswordEncoder passwordEncoder;

    @Override
    public String encrypt(String rawPassword) {
        // return passwordEncoder.encode(rawPassword);
//        return "test encrypt password";
        throw new UnsupportedOperationException();
    }
}
