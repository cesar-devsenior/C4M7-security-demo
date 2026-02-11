package com.devsenior.cdiaz.security.util;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .claims(claims)
                .signWith(getSignInKey())
                .compact();
    }

    public boolean validateToken(String token) {
        // Verificar si el token está firmado correctamente
        // Verificar si el token no ha expirado
        var exp = extractClaim(token, Claims::getExpiration);
        if(exp.before(new Date())){
            return false;
        }

        return true;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        var claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseUnsecuredClaims(token)
                .getPayload();

        return claimsResolver.apply(claims);
    }

    private SecretKey getSignInKey() {
        var keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
