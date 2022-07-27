package com.andresmastracchio.yoprogramo.config.security.jwt;

import com.andresmastracchio.yoprogramo.config.security.MainUser;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        MainUser mainUser = (MainUser) authentication.getPrincipal();
        return Jwts.builder().setSubject(mainUser.getUsername())
                .setIssuedAt(new Date())
                //.setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(20).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getNombreUsuarioFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("token mal formado " +e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("token no soportado " +e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("token expirado " +e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("token vacío " +e.getMessage());
        } catch (SignatureException e) {
            log.error("error en la firma " +e.getMessage());
        }
        return false;
    }
}
