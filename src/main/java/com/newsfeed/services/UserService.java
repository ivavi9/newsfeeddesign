package com.newsfeed.services;

import com.newsfeed.dtos.UserDTO;
import com.newsfeed.models.User;
import com.newsfeed.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public User signUp(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setHashedPassword(encodedPassword);
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }


    public User login(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail());
        if (user != null) {

            if (passwordEncoder.matches(userDTO.getPassword(), user.getHashedPassword())) {
                return user;
            }
        }
        return null;
    }
}
