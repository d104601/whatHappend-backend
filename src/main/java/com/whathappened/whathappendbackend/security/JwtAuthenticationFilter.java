package com.whathappened.whathappendbackend.security;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;


@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter {
    @Value("${jwt.secret-key}")
    private String secretKey;

    private Key key;

    public void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
