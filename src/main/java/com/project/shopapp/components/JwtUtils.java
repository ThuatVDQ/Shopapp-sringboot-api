package com.project.shopapp.components;

import com.project.shopapp.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.secretKey}")
    private String secretKey;
    public String generateToken(User user) {
        //properties -> claims
        Map<String, Object> claims = new HashMap<>();
        //this.generateSecretKey();
        claims.put("phoneNumber", user.getPhoneNumber());
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            //Can use logger here, instead of System.out.println
            System.out.println("Cannot generate token, error: " + e.getMessage());
            return null;
        }

    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);

        return Encoders.BASE64.encode(key);
    }
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            //Can use logger here, instead of System.out.println
            System.out.println("Cannot extract claims, error: " + e.getMessage());
            return null;
        }
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    //check expiration
    private boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extractPhoneNumber(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        String phoneNumber = this.extractPhoneNumber(token);
        return (phoneNumber.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
