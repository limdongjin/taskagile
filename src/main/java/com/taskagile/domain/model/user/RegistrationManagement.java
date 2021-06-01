package com.taskagile.domain.model.user;

import com.taskagile.domain.common.security.PasswordEncryptor;
import org.springframework.stereotype.Component;

@Component
public class RegistrationManagement {
    private UserRepository userRepository;
    private PasswordEncryptor passwordEncryptor;

    public RegistrationManagement(UserRepository userRepository, PasswordEncryptor passwordEncryptor) {
        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    public User register(String username, String emailAddress, String password) throws RegistrationException {
        // [TODO] 중복 유저 확인 로직 모듈화
        if(userRepository.findByUsername(username) != null)
            throw new UsernameExistException();
        if(userRepository.findByEmailAddress(emailAddress) != null)
            throw new EmailAddressExistException();

        String encryptedPassword = passwordEncryptor.encrypt(password);
        User newUser = User.create(username, emailAddress, encryptedPassword);
        userRepository.save(newUser);
        return newUser;
    }
}
