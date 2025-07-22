package com.audittrack.auditscheduler.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "audittrack-secret-key"; // puedes cambiar esto por una clave más segura

    // Genera un token JWT para un usuario
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // fecha actual
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Extrae el nombre de usuario del token
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Verifica si el token es válido (no expirado)
    public boolean isTokenValid(String token) {
        return !getClaims(token).getExpiration().before(new Date());
    }

    // Extrae los claims del token
    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
