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

/**
 * A service class responsible for user-related functionality, such as signing up, logging in, and following other users.
 */
@Service
public class UserService {

    @Autowired
    FollowerFollowingRepository followerFollowingRepository;
    @Autowired
    UserRepository userRepository;

    // Initializing a BCryptPasswordEncoder for password hashing
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Registers a new user with the provided user details.
     *
     * userDTO User data transfer object containing user details.
     * return User object saved in the database.
     */
    public User signUp(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setHashedPassword(encodedPassword);
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }


    /**
     * Authenticates the user based on the provided email and password.
     *
     * userDTO User data transfer object containing user email and password.
     * return User object if authenticated, null otherwise.
     */
    public User login(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail());
        if (user != null) {

            if (passwordEncoder.matches(userDTO.getPassword(), user.getHashedPassword())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Adds a follow relationship between the current user and the user specified by email.
     *
     * userDTO User data transfer object containing followUserEmail and currentUser.
     */
    public void followUser(UserDTO userDTO) {
        User followUser = userRepository.findByEmail(userDTO.getFollowUserEmail());

        // If the user to be followed doesn't exist, print an error message and return
        if (followUser == null) {

            System.out.println("The follow user does not exist");
            return;
        }

        User currentUser = userDTO.getCurrentUser();

        // Check if the current user and follow user are the same and print a message if they are
        if(currentUser.getId() ==  followUser.getId()){
            // throw exception in the future here
            System.out.println("Current user and follow user are the same....");
            return;

        }

        //Create a FollowerFollowingId object with the current user and follow user IDs
        FollowerFollowingId id = new FollowerFollowingId(currentUser.getId(),followUser.getId());

        // Check if the current user is already following the follow user and print a message if they are
        if (followerFollowingRepository.existsByFollowerIdAndFollowingId(currentUser.getId(), followUser.getId())) {
            System.out.println("You are already following this user.");
            return;
        } else {
            // If not following, create a new FollowerFollowing object and save it to the repository
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
