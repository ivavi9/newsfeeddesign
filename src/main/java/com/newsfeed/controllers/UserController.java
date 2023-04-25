package com.newsfeed.controllers;


import com.newsfeed.dtos.UserDTO;
import com.newsfeed.exceptions.EmailAlreadyExistsException;
import com.newsfeed.exceptions.IncorrectPasswordException;
import com.newsfeed.exceptions.SelfFollowException;
import com.newsfeed.exceptions.UserNotFoundException;
import com.newsfeed.models.User;
import com.newsfeed.services.UserService;
import com.newsfeed.singletons.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    // Method to sign up a new user
    public void signup(String[] userInput) {
        if (userInput.length < 4) {
            System.out.println("Invalid input. Usage: signup name email password expects 3 words separated in the fashion");
            return;
        }
        String name = userInput[1];
        String email = userInput[2];
        String password = userInput[3];
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setName(name);
        userDTO.setPassword(password);
        try {
            User user = userService.signUp(userDTO);
        } catch (EmailAlreadyExistsException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("User created successfully........");

    }


    // Method to log in a user
    public void login(String[] userInput) {
        if (userInput.length < 3) {
            System.out.println("Invalid input. Usage: login email password expects 3 words separated in the fashion");
            return;
        }
        String email = userInput[1];
        String password = userInput[2];

        UserDTO userDTO = new UserDTO();
        userDTO.setPassword(password);
        userDTO.setEmail(email);


        User user = null;
        try {
            user = userService.login(userDTO);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        } catch (IncorrectPasswordException e){
            System.out.println(e.getMessage());
            return;
        }
        if (user != null) {
            Session.getSession().setUser(user);
            System.out.println("Login successful........");
        } else {
            System.out.println("Login failed. Invalid email or password....... retry again");
        }
    }


    // Method to log out a user
    public void logout() {
        if(Session.getSession().getUser() == null){
            System.out.println("Already logged out.......");
            return;
        }
        Session.getSession().setUser(null);
        System.out.println("Logout successful........");
    }

    // Method to follow another user
    public void followUser(String[] inputParts) {
        if (inputParts.length < 2) {
            System.out.println("Invalid input. Usage: followUser followUserId expects 2 words separated in the fashion");
            return;
        }
        String followUserEmail = inputParts[1];
        User user = Session.getSession().getUser();
        if (user == null) {
            System.out.println("Please login first.......");
            return;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setCurrentUser(user);
        userDTO.setFollowUserEmail(followUserEmail);

        try {
            userService.followUser(userDTO);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SelfFollowException e){
            System.out.println(e.getMessage());
        }


    }
}
