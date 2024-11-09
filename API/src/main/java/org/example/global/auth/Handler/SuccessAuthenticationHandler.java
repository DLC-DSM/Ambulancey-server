package org.example.global.auth.Handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.global.auth.token.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.example.global.auth.user.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SuccessAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider provider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        String token = provider.generateAccessToken(user);

        if(token != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            response.addHeader("Authorization", token);
        }
    }
}
