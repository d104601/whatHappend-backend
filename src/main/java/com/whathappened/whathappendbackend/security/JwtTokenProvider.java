package com.whathappened.whathappendbackend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final Logger logger;

    private final Key key;

    private final long expiration;

    public JwtTokenProvider(
            @Value("${jwt.expiration}") long expiration,
            @Value("${jwt.secret-key}") String secretKey
    ) {
        this.logger = LoggerFactory.getLogger(JwtTokenProvider.class);
        // 512 bits = 64 bytes
        byte[] secretKeyBytes = new byte[64];
        byte[] secretKeyBytesFromConfig = secretKey.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(secretKeyBytesFromConfig, 0, secretKeyBytes, 0, secretKeyBytesFromConfig.length);
        this.key = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
        this.expiration = expiration;
    }

    public String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.error("Invalid JWT token: ", e);
            return false;
        }
    }
}
