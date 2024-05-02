package com.fmap.config;

import com.fmap.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrfConfig -> csrfConfig.disable())
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/user/**").authenticated()
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
//                                .anyRequest().permitAll()
//                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/loginForm")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/")
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/loginForm")
                                .userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig.userService(principalOauth2UserService)
                                )
                );

        return http.build();
    }

}
