package tech.maou.patitasalrescate.service.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${app.secret_key}")
    private String secretKey;

    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(new HashMap<>())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public String getTokenFromRequest(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "auth_token");

        return cookie != null ? cookie.getValue() : null;
    }

    @Override
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {

        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private Date getExpirationDateFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

}
