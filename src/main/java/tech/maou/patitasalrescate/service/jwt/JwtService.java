package tech.maou.patitasalrescate.service.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    String getTokenFromRequest(HttpServletRequest request);
    String getUsernameFromToken(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
