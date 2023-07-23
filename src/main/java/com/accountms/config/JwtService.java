package com.accountms.config;

import com.accountms.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    public final static String SECRET_KEY ="";

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolve) {
        final Claims claims = getAllClaims(token);
        return claimsResolve.apply(claims);
    }

    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build()
                .parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    private String generateToken(Map<String, Object> extraClaims, User user) {
        return Jwts.builder().setClaims(extraClaims).setSubject(user.getUsername()).
                claim("authorities", user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() * 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        return (extractEmail(token).equals(user.getUsername())) && !isTokenExipired(token);
    }

    public boolean isTokenExipired(String token) {
        return extractExperition(token).before(new Date());
    }

    private Date extractExperition(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
