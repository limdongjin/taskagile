package com.taskagile.domain.model.user;

import com.taskagile.domain.common.security.PasswordEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationManagementTest {
    // @Mock 을 사용하기 위해서는 위의 ExtendWith(..) 를 해줘야한다.
    @Mock private UserRepository userRepositoryMock;
    @Mock private PasswordEncryptor passwordEncryptorMock;
    private RegistrationManagement registrationManagement;

    @BeforeEach
    void setUp() {
        // 아래 주석 코드 같이하면 @Mock 어노테이션과 @ExtendWith(..) 를 없앨수있다.
        // userRepositoryMock = Mockito.mock(UserRepository.class);
        // passwordEncryptorMock = Mockito.mock(PasswordEncryptor.class);
        registrationManagement = new RegistrationManagement(userRepositoryMock, passwordEncryptorMock);
    }

    @Test
    @DisplayName("이미 존재하는 사용자 이름이라고 레포지토리가 알려줬다면, registrationManagement 는 예외를 반환해야한다. ")
    public void register_existedUsername_shouldFail(){
        String username = "existUserstub";
        String emailAddress = "helloworld@gmail.com";
        String password = "mypassword";

        when(userRepositoryMock.findByUsername(username))
                .thenReturn(new User());

        assertThrows(UsernameExistException.class, () ->
                registrationManagement.register(username, emailAddress, password));
    }

    @Test
    @DisplayName("이미 존재하는 이메일 주소라고 레포지토리가 알려줬다면, registrationManagement 는 예외를 반환해야한다. ")
    public void register_existEmailAddress_shouldFail(){
        String username = "iamuser";
        String emailAddress = "exiastuserstuaaa@gmail.com";
        String password = "mypassword";

        when(userRepositoryMock.findByEmailAddress(emailAddress))
                .thenReturn(new User());

        assertThrows(EmailAddressExistException.class, () ->
                registrationManagement.register(username, emailAddress, password));
    }

    @Test
    @DisplayName("중복 유저 체크, 패스워드 암호화 함수를 적절히 호출하는지 확인한다. ")
    public void register_newUser_shouldSucceed() throws Exception{
        String username = "iamuser";
        String emailAddress = "serstuaaa@gmail.com";
        String password = "mypassword";
        String encryptedPassword = "Encrypted Passsswordddddd!!@#!!";

        when(userRepositoryMock.findByUsername(username))
                .thenReturn(null);
        when(userRepositoryMock.findByEmailAddress(emailAddress))
                .thenReturn(null);
        when(userRepositoryMock.save(any()))
                .thenReturn(null);
        when(passwordEncryptorMock.encrypt(password))
                .thenReturn(encryptedPassword);

        User savedUser = registrationManagement.register(username, emailAddress, password);
        InOrder inOrder = Mockito.inOrder(userRepositoryMock);
        inOrder.verify(userRepositoryMock).findByUsername(username);
        inOrder.verify(userRepositoryMock).findByEmailAddress(emailAddress);
        inOrder.verify(userRepositoryMock).save(any());

        verify(passwordEncryptorMock).encrypt(password);
        assertEquals(encryptedPassword, savedUser.getPassword());
    }

}