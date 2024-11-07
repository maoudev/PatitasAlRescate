package tech.maou.patitasalrescate.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtilImpl implements CookieUtil {
    @Override
    public String createCookie(String key, String value, long maxAge) {
        return ResponseCookie.from(key, value)
                .httpOnly(true)
                .path("/")
                .domain("localhost")
                .maxAge(60 * 60 * 24)
                .build()
                .toString();
    }

    @Override
    public void clearCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}
