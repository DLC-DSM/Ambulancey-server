package org.example.global.auth.user.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.User.UserEntity;
import org.example.global.auth.user.dto.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserRepository userRepository;

    @Transactional
    public void registerUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userRepository.save(userEntity);
    }
}
