package com.newsfeed.controllers;

import com.newsfeed.dtos.PostDTO;
import com.newsfeed.models.PostVote;
import com.newsfeed.models.Vote;
import com.newsfeed.models.VoteType;
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
        postDTO.setReplyText(content.toString());
        postService.createPost(postDTO);




    }

    public void replyToPost(String[] inputParts) {
        if(inputParts.length < 3) {
            System.out.println("Invalid input. Usage: reply postId comment");
            return;
        }
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(Long.parseLong(inputParts[1]));
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 2; i < inputParts.length; i++) {
            stringBuilder.append(inputParts[i]);
            if(i != inputParts.length - 1)
                stringBuilder.append(" ");
        }
        postDTO.setReplyText(stringBuilder.toString());
        postService.replyToPost(postDTO);

    }

    public void voteOnPost(String[] inputParts) {
        if(inputParts.length < 2) {
            System.out.println("Invalid input. Usage: vote postId");
            return;
        }
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(Long.parseLong(inputParts[1]));

        if(inputParts[0] .equals("upvote")) {
            postDTO.setVoteType(VoteType.UPVOTE);
        } else if (inputParts[0].equals("downvote")) {
            postDTO.setVoteType(VoteType.DOWNVOTE);
        }
        postService.voteOnPost(postDTO);

    }

    public void showNewsFeed(String[] inputParts) {
    }
}
