package az.unibank.msauth.util;

import az.unibank.msauth.model.UserClaims;
import az.unibank.msauth.model.enums.Role;
import az.unibank.msauth.model.view.AuthDetailsView;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserClaims userClaims) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return Jwts.builder()
                .setSubject(userClaims.getUsername())
                .setClaims(addClaims(userClaims))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(calendar.getTime())
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public boolean isTokenValid(String token) {
        return isTokenExpired(token);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && isTokenExpired(token);
    }

    public AuthDetailsView extractClaims(String token) {
        var claims = extractAllClaims(token.substring(7));
        var role = claims.get("role", String.class);
        var userId = claims.get("userId", Long.class);
        var username = claims.get("username", String.class);

        return AuthDetailsView.of(userId, username, Role.valueOf(role));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return !extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Map<String, Object> addClaims(UserClaims userClaims) {
        var claims = new HashMap<String, Object>();
        claims.put("userId", userClaims.getUserId());
        claims.put("role", userClaims.getRole());
        claims.put("username", userClaims.getUsername());

        return claims;
    }
}