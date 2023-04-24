package com.newsfeed.controllers;

import com.newsfeed.dtos.PostDTO;
import com.newsfeed.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    public void createPost(String[] userInput) {
        if (userInput.length < 2) {
            System.out.println("Invalid input. Usage: post content");
            return;
        }
        PostDTO postDTO = new PostDTO();

        StringBuilder  content = new StringBuilder();

        for (int i = 1; i < userInput.length; i++) {
            content.append(userInput[i]);
            if(i!= userInput.length-1)
                content.append(" ");
        }
        postDTO.setContent(content.toString());
        postService.createPost(postDTO);




    }

    public void replyToPost(String[] inputParts) {
    }

    public void voteOnPost(String[] inputParts) {
    }

    public void showNewsFeed(String[] inputParts) {
    }
}
