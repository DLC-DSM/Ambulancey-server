package org.example.global.config;

import lombok.RequiredArgsConstructor;
import org.example.global.auth.Handler.SuccessAuthenticationHandler;
import org.example.global.auth.filter.JwtFilter;
import org.example.global.auth.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.example.global.auth.jwt.JwtProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtProvider jwtProvider;
    private final SuccessAuthenticationHandler successAuthenticationHandler;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain SecurityConfig(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(form -> form.successHandler(successAuthenticationHandler))


                .authorizeHttpRequests(authorizeRequests ->
                            authorizeRequests
                                    .requestMatchers("/login","/user/register").permitAll()
                                    .requestMatchers("/hospital/application","/hospital/update","/hospital/delete").hasAuthority("")
                                    .anyRequest().authenticated()
                )


                .sessionManagement((session)->{
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);


        return http.build();

    }
}
