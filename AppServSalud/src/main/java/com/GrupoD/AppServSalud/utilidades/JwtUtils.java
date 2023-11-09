package com.GrupoD.AppServSalud.utilidades;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.time.expiration}")
    private String TIME_EXPIRATION;

    @Value("${jwt.secret.calificacion}")
    private String SECRET_KEY_CALIFICACION;

    @Value("${jwt.time.expiration.calificacion}")
    private String TIME_EXPIRATION_CALIFICACION;
    
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(TIME_EXPIRATION)))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Key getSignatureKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public String getEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    public String generateTokenCalificacion(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(TIME_EXPIRATION_CALIFICACION)))
                .signWith(getSignatureKey2(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    public boolean validateTokenCalificacion(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSignatureKey2())
                .build()
                .parseClaimsJws(token)
                .getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public Key getSignatureKey2(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY_CALIFICACION);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmailCalificacion(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignatureKey2())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
