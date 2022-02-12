package com.forum.service;

import com.forum.model.DTO.RegisterUserDTO;
import com.forum.model.User;
import com.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
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

    public boolean register(RegisterUserDTO userDTO) throws IOException {
        User user = repository.findByEmail(userDTO.getEmail());
        if (user != null) {
            return false;
        }

        BufferedImage bufferedImage = ImageIO.read(userDTO.getImage().getInputStream());
        File outfile = new File("src/main/resources/static/imgs/" + userDTO.getEmail() + ".png");
        ImageIO.write(bufferedImage, "png", outfile);
        user = new User(userDTO.getEmail(), userDTO.getFullName(), passwordEncoder.encode(userDTO.getPassword()), outfile.getPath());
        repository.save(user);
        return true;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
