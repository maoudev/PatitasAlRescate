package tech.maou.patitasalrescate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.maou.patitasalrescate.persistence.entity.UserEntity;
import tech.maou.patitasalrescate.persistence.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name())));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
