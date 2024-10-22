package org.example.global.auth.Handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.global.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.example.global.auth.user.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class SuccessAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider provider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
        String token = provider.generateAccessToken((CustomUserDetails) authentication.getDetails());
        if(token != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            response.addHeader("Authorization", token);
        }
    }
}
