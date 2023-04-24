package com.newsfeed.controllers;

import com.newsfeed.dtos.PostDTO;
import com.newsfeed.models.VoteType;
import com.newsfeed.services.CommentService;
import com.newsfeed.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentController {


    @Autowired
    CommentService commentService;
    // Method to reply to a comment
    public void replyToComment(String[] userInput) {
        if(userInput.length < 3) {
            System.out.println("Invalid input. Usage: replycomment commentId comment");
            return;
        }
        PostDTO postDTO = new PostDTO();
        postDTO.setCommentId(Long.parseLong(userInput[1]));
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 2; i < userInput.length; i++) {
            stringBuilder.append(userInput[i]);
            if(i != userInput.length - 1)
                stringBuilder.append(" ");
        }
        postDTO.setReplyText(stringBuilder.toString());
        commentService.replyToComment(postDTO);

    }

    // Method to vote on a comment
    public void voteOnComment(String[] userInput) {
        if(userInput.length < 2) {
            System.out.println("Invalid input. Usage: votecomment commentId");
            return;
        }
        PostDTO postDTO = new PostDTO();
        postDTO.setCommentId(Long.parseLong(userInput[1]));

        if(userInput[0] .equals("upvotecomment")) {
            postDTO.setVoteType(VoteType.UPVOTE);
        } else if (userInput[0].equals("downvotecomment")) {
            postDTO.setVoteType(VoteType.DOWNVOTE);
        }
        commentService.voteOnComment(postDTO);

    }


}
