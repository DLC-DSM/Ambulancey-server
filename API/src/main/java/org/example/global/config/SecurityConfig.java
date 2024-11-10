package org.example.global.config;

import lombok.RequiredArgsConstructor;
import org.example.global.auth.Handler.SuccessAuthenticationHandler;
import org.example.global.auth.filter.JwtFilter;
import org.example.global.auth.filter.LoginFilter;
import org.example.global.auth.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.example.global.auth.token.JwtProvider;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtProvider jwtProvider;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain SecurityConfig(HttpSecurity http) throws Exception {
        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration),jwtProvider,customUserDetailsService);
        loginFilter.setFilterProcessesUrl("/user/login");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)

                .logout(logout -> logout.logoutUrl("/user/logout"))
                .authorizeHttpRequests(authorizeRequests ->
                            authorizeRequests
                                    .requestMatchers("/user/login","/user/register","/user/hospital_register").permitAll()
                                    .requestMatchers("/hospital/application","/hospital/list","hospital/info").hasAuthority("ROLE_USER")
                                    .requestMatchers("/hospital/*").hasAuthority("ROLE_HOSPITAL")
                                    .anyRequest().authenticated()
                )

                .userDetailsService(customUserDetailsService)
                .sessionManagement((session)->{
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(loginFilter,UsernamePasswordAuthenticationFilter.class);


        return http.build();

    }
}
