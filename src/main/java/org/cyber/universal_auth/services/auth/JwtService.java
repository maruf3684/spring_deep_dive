package org.cyber.universal_auth.services.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;


@Service
public class JwtService {
    private static final String SECRET_KEY = "YOUR_SECRET_KEY_STRING_YOUR_SECRET_KEY_STRING";
    public SecretKey getSignatureKey(){
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String createToken(String userName){
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        String token = Jwts
                .builder()
                .subject(userName)
                .issuedAt(now)
                .expiration(validity)
                .claims()
                .add("app_name","universal_auth")
                .add("used_for","auth")
                .and()
                .signWith(getSignatureKey(),Jwts.SIG.HS256)
                .compact();
        return token;
    }
    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignatureKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }
}









//    public boolean isValid(String token, UserDetails user) {
//        String username = extractUsername(token);
//
//        boolean validToken = tokenRepository
//                .findByToken(token)
//                .map(t -> !t.isLoggedOut())
//                .orElse(false);
//
//        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
//    }