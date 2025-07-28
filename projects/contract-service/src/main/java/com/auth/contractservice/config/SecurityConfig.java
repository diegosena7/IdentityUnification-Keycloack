package com.auth.contractservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import com.auth.contractservice.controller.LogoutHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    
   private String[] permmitedApiUris = {
        "/api/v1/register",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/favicon.ico",
        "/images/**", 
        "/css/**",
        "/",
        "/api",
        "/openapi",
        "/swagger"
   };

    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
        .securityMatcher("/api/**", "/swagger-ui/**", "/swagger/**", "/v3/api-docs/**")
        .csrf(csrf -> csrf.disable())
        //.cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .cors(c -> c.disable())
        //.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        .authorizeHttpRequests(auth -> auth
                .requestMatchers(permmitedApiUris).permitAll()
                .anyRequest().authenticated()
        )
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Autowired
    private LogoutHandler logoutHandler;


    @Bean
    SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/**")
            .authorizeHttpRequests(
                // Permite acesso total a home e as imagens
                customizer -> customizer.requestMatchers(permmitedApiUris).permitAll()
                      // Todos os requests devem estar autenticados!
                      .anyRequest().authenticated()
            )
            .oauth2Login(Customizer.withDefaults())
            .logout(
                customizer -> customizer.logoutRequestMatcher(PathPatternRequestMatcher.withDefaults().matcher("/logout"))
                                        .addLogoutHandler(logoutHandler)
            );
        return http.build();
    }
}
