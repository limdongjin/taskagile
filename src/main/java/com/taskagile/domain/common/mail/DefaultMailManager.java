package com.taskagile.domain.common.mail;

import org.springframework.stereotype.Component;

@Component
public class DefaultMailManager implements MailManager {
    @Override
    public void send(String emailAddress, String subject, String template, MessageVariable... messageVariables) {
        // [TODO] DefaultMailManager 구현 하기
        throw new UnsupportedOperationException();
    }
}
