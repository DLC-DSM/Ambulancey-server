package org.example.global.auth.user.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.User.UserEntity;
import org.example.domain.User.UserRoleEntity;
import org.example.global.auth.user.dto.User;
import org.example.global.auth.user.exception.AlreadyRegisteredException;
import org.example.global.auth.user.exception.CannotFoundUserException;
import org.example.global.auth.user.exception.NotAllowedUsernameException;
import org.example.repository.UserRepository;
import org.example.repository.UserRoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    public void registerUser(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            return;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(encoder.encode(user.getPassword()));

        if(user.getUsername().contains("병원")){
            throw NotAllowedUsernameException.EXCEPTION;
        }

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUser(userEntity);
        userRoleEntity.setRole("ROLE_USER");

        List<UserRoleEntity> userRoleEntities = new ArrayList<>();
        userRoleEntities.add(userRoleEntity);
        userEntity.setUserRoles(userRoleEntities);

        userRepository.save(userEntity);
        userRoleRepository.save(userRoleEntity);
    }

    public void registerHospital(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw AlreadyRegisteredException.EXCEPTION;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(encoder.encode(user.getPassword()));



        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUser(userEntity);
        userRoleEntity.setRole("ROLE_HOSPITAL");

        List<UserRoleEntity> userRoleEntities = new ArrayList<>();
        userRoleEntities.add(userRoleEntity);
        userEntity.setUserRoles(userRoleEntities);

        userRepository.save(userEntity);
        userRoleRepository.save(userRoleEntity);
    }


    @Transactional
    public void deleteUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(()->CannotFoundUserException.cannotFoundUserException);
        userRepository.delete(userEntity);
    }

    public void updateUsername(User user, String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(()->CannotFoundUserException.cannotFoundUserException);
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(userEntity);
    }

    public UserEntity selectUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public boolean userConfirm(String username,String password){
        UserEntity findUser = selectUserByUsername(username);
        return encoder.matches(password, findUser.getPassword());
    }


}
