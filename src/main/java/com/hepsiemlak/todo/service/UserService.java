package com.hepsiemlak.todo.service;

import com.hepsiemlak.todo.exception.UserNotLoginException;
import com.hepsiemlak.todo.model.User;
import com.hepsiemlak.todo.model.UserPrincipal;
import com.hepsiemlak.todo.model.dto.UserDto;
import com.hepsiemlak.todo.repository.UserRepository;
import com.hepsiemlak.todo.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            log.error("User not found");
            throw new UsernameNotFoundException(username);
        }

        return new UserPrincipal(user.get());
    }



}
