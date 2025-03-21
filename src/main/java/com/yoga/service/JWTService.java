package com.yoga.service;

import com.yoga.component.Role;
import io.jsonwebtoken.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;



@Service
public class JWTService {

    private Logger logger = LoggerFactory.getLogger(JWTService.class);

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority().replace("Role_",""))
                .toList();
        claims.put("roles",roles);

       // logger.info("user mail id : "+userDetails.getUsername()+" role is : "+roles);

      return Jwts.builder()
              .claims()
              .add(claims)
              .subject(userDetails.getUsername())
              .issuedAt(new Date(System.currentTimeMillis()))
              .expiration(new Date(System.currentTimeMillis()+86400000))
              .and()
              .signWith(getKey())
              .compact();
    }


    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String extractEmailId(String token){
         return extractClaims(token,Claims::getSubject);
    }

    private <T> T extractClaims(String token,Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

   private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
   }

   public Boolean validateToken(String token,UserDetails userDetails){
        final String emailId = extractEmailId(token);
        return (emailId.equals(userDetails.getUsername()) && !isTokenExpired(token));
   }

   private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
   }

   private Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
   }

   public List<Role> extractRole(String token){
        List<String> roleString = extractClaims(token,claims->claims.get("roles",List.class));

        return roleString.stream()
                .map(role->Role.valueOf(role.replace("ROLE_","")))
                .toList();
    }
}
