package com.newsfeed.services;

import com.newsfeed.dtos.UserDTO;
import com.newsfeed.models.FollowerFollowing;
import com.newsfeed.models.FollowerFollowingId;
import com.newsfeed.models.User;
import com.newsfeed.repositories.FollowerFollowingRepository;
import com.newsfeed.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    FollowerFollowingRepository followerFollowingRepository;
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

    public void followUser(UserDTO userDTO) {
        User followUser = userRepository.findByEmail(userDTO.getFollowUserEmail());
        if (followUser == null) {

            System.out.println("The follow user does not exist");
            return;
        }

        User currentUser = userDTO.getCurrentUser();

        if(currentUser.getId() ==  followUser.getId()){
            // throw exception in the future here
            System.out.println("Current user and follow user are the same....");
            return;

        }


        FollowerFollowingId id = new FollowerFollowingId(currentUser.getId(),followUser.getId());

        if (followerFollowingRepository.existsById(id)) {
            System.out.println("You are already following this user.");
            return;
        } else {
            FollowerFollowing followerFollowing = new FollowerFollowing();
            followerFollowing.setId(id);
            followerFollowing.setFollower(currentUser);
            followerFollowing.setFollowing(followUser);

            followerFollowingRepository.save(followerFollowing);

            System.out.println("You are now following " + followUser.getName() + ".");
            return;
        }



    }
}
