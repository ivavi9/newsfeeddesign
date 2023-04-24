package com.newsfeed.controllers;


import com.newsfeed.dtos.UserDTO;
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
        User user = userService.signUp(userDTO);

        System.out.println("User created successfully........");

    }

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


        User user = userService.login(userDTO);
        if (user != null) {
            Session.getSession().setUser(user);
            System.out.println("Login successful........");
        } else {
            System.out.println("Login failed. Invalid email or password....... retry again");
        }
    }


    public void logout() {
        if(Session.getSession().getUser() == null){
            System.out.println("Already logged out.......");
            return;
        }
        Session.getSession().setUser(null);
        System.out.println("Logout successful........");
    }

    public void followUser(String[] inputParts) {
    }
}
