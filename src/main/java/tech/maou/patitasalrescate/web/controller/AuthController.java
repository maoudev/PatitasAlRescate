package tech.maou.patitasalrescate.web.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.maou.patitasalrescate.persistence.entity.DTO.AuthResponseDTO;
import tech.maou.patitasalrescate.persistence.entity.DTO.UserCredentialsDTO;
import tech.maou.patitasalrescate.service.auth.AuthService;
import tech.maou.patitasalrescate.utils.CookieUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @Autowired
    public AuthController(AuthService authService, CookieUtil cookieUtil) {
        this.authService = authService;
        this.cookieUtil = cookieUtil;
    }

    @PostMapping("")
    public ResponseEntity<Void> login(
            @RequestBody UserCredentialsDTO credentials
            ) {
        String token = this.authService.login(credentials);
        String cookie = this.cookieUtil.createCookie("auth_token", token, 60 * 60 * 24);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        this.cookieUtil.clearCookie(response, "auth_token");
        return ResponseEntity.ok().build();
    }
}   
