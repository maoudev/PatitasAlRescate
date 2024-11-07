package tech.maou.patitasalrescate.utils;

import jakarta.servlet.http.HttpServletResponse;

public interface CookieUtil {
    String createCookie(String key, String value, long maxAge);
    void clearCookie(HttpServletResponse response, String key);
}
