package org.example.global.auth.user;

import lombok.Builder;
import org.example.domain.User.UserRoleEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
public record CustomUserDetails(
        String username,
        String password,
        List<SimpleGrantedAuthority> authorities

) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
