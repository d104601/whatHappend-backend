package com.whathappened.whathappendbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
    // CORS
    @Value("${cors.allowed-origins}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/news/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/weather/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/user/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/user/test").permitAll()
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(
                session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    // bcrypt password encoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
