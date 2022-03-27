package com.forum.service;

import com.forum.model.dto.RegisterUserDto;
import com.forum.model.User;
import com.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return (UserDetails) user;
    }

    public boolean register(RegisterUserDto userDTO) throws IOException {
        User user = repository.findByEmail(userDTO.getEmail());
        if (user != null) {
            return false;
        }

        user = User.builder()
                .email(userDTO.getEmail())
                .fullName(userDTO.getFullName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role("ROLE_USER")
                .enabled(true)
                .build();
        repository.save(user);
        return true;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
