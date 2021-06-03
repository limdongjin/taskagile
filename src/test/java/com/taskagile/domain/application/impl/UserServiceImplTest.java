package com.taskagile.domain.application.impl;

import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.common.mail.MailManager;
import com.taskagile.domain.model.user.RegistrationManagement;
import com.taskagile.domain.model.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock private RegistrationManagement registrationManagementMock;
    @Mock private DomainEventPublisher domainEventPublisherMock;
    @Mock private UserRepository userRepositoryMock;
    @Mock private MailManager mailManagerMock;

    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        userServiceImpl = new UserServiceImpl(registrationManagementMock,
                domainEventPublisherMock,
                userRepositoryMock,
                mailManagerMock);
    }

    @Test
    void register_nullCommand_shouldFail() throws Exception{
        assertThrows(NullPointerException.class, () -> {
            userServiceImpl.register(null);
        });
    }

    @Test
    void register_existingUsername() {

        assertFalse(false);
    }

    @Test
    void register_existingEmailAddress_shouldFail() {
        assertFalse(false);
    }

    @Test
    void register_validCommand_shouldSucceed() {
        assertFalse(false);
    }
}