package com.eddiejrojas.SXMproject.security;

import com.eddiejrojas.SXMproject.users.LoginDetails;
import com.eddiejrojas.SXMproject.users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTUtils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expires.minutes}")
    private int JWT_TOKEN_EXPIRES_MINUTES;

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(LoginDetails loginDetails){
        Map<String, Object> claims = new HashMap<>();
        System.out.println("LOOK WE'RE HERE HERE ARE OUR DETAILS  "+ loginDetails);
        return generateToken(claims, loginDetails.getEmail());
    }

    private String generateToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_EXPIRES_MINUTES * 60000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(String token, User userDetails){
        //TODO figure out how this actually verifies the validity of the token.
        final String email = getUsernameFromToken(token); //TODO try to make it make sense semantically. As we refer to emails as usernames.
        System.out.println("GOT USERNAME FROM TOKEN " + userDetails.getEmail());
        return (email.equals(userDetails.getEmail()) && !isTokenExpired(token));
    }
}
