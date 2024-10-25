package org.example.global.auth.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.domain.User.UserEntity;
import org.example.global.auth.user.exception.CannotFoundUserException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.example.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserEntity user = userRepository.findByUsername(username).orElseThrow(CannotFoundUserException::new);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getUserRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        });

       return CustomUserDetails.builder()
               .username(user.getUsername())
               .password(user.getPassword())
               .authorities(authorities)
               .build();
    }
}
