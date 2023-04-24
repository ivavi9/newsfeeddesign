package com.newsfeed;

import com.newsfeed.controllers.CommentController;
import com.newsfeed.controllers.PostController;
import com.newsfeed.controllers.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Scanner;

// Enable JPA auditing and Spring Boot application
@EnableJpaAuditing
@SpringBootApplication
public class NewsfeedApplication implements CommandLineRunner{
    @Autowired
    UserController userController;
    @Autowired
    PostController postController;

    @Autowired
    CommentController commentController;

    // Initialize the Scanner to read user input
    Scanner scanner = new Scanner(System.in);

    // Main method to start the application
    public static void main(String[] args) {
        SpringApplication.run(NewsfeedApplication.class, args);
        Scanner scanner = new Scanner(System.in);

    }


    // This method is called after the application starts
    @Override
    public void run(String ...args){

        // Run the command loop until the user exits the application
        while (true) {
            System.out.print(">>> ");
            String input = scanner.nextLine();
            String[] userInput = input.split(" ");
            String command = userInput[0].toLowerCase();

            // Process the user's command
            switch (command) {
                case "signup":
                    userController.signup(userInput);
                    break;
                case "login":
                    userController.login(userInput);
                    break;
                case "logout":
                    userController.logout();
                    break;
                case "post":
                    postController.createPost(userInput);
                    break;
                case "follow":
                    userController.followUser(userInput);
                    break;
                case "reply":
                    postController.replyToPost(userInput);
                    break;
                case "upvote":
                case "downvote":
                    postController.voteOnPost(userInput);
                    break;
                case "replycomment":
                    commentController.replyToComment(userInput);
                    break;
                case "upvotecomment":
                case "downvotecomment":
                    commentController.voteOnComment(userInput);
                    break;
                case "shownewsfeed":
                    postController.showNewsFeed(userInput);
                    break;
                case "experiment":
                    postController.experiment(userInput);
                    break;

                case "exit":
                    System.out.println("Exiting from the application...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command entered. Please try again.");
            }
        }
    }
}
