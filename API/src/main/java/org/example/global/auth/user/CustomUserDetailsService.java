package org.example.global.auth.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.domain.User.UserEntity;
import org.example.global.auth.user.exception.CannotFoundUserException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.example.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserEntity user = userRepository.findByUsername(username).orElseThrow(CannotFoundUserException::new);

       return CustomUserDetails.builder()
               .username(user.getUsername())
               .password(user.getPassword())
               .authorities(user.getUserRoles())
               .build();
    }
}
