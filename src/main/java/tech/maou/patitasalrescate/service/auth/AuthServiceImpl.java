package tech.maou.patitasalrescate.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import tech.maou.patitasalrescate.exception.UserNotFoundException;
import tech.maou.patitasalrescate.persistence.entity.DTO.UserCredentialsDTO;
import tech.maou.patitasalrescate.service.jwt.JwtService;

@Service
public class    AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(UserDetailsService userDetailsService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public String login(UserCredentialsDTO credentials) throws UserNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
        UserDetails user = this.userDetailsService.loadUserByUsername(credentials.getUsername());

        return this.jwtService.generateToken(user);
    }
}
