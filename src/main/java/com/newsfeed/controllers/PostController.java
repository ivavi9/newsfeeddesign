package com.newsfeed.controllers;

import com.newsfeed.dtos.PostDTO;
import com.newsfeed.models.*;
import com.newsfeed.services.PostService;
import com.newsfeed.singletons.Session;
import com.newsfeed.strategies.shownewsfeed.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    private String formatTimeDifference(Date postCreationTime) {
        long diff = new Date().getTime() - postCreationTime.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;
        long years = days / 365;

        if (years > 0) {
            return years + (years == 1 ? " year" : " years") + " ago";
        } else if (weeks > 0) {
            return weeks + (weeks == 1 ? " week" : " weeks") + " ago";
        } else if (days > 0) {
            return days + (days == 1 ? " day" : " days") + " ago";
        } else if (hours > 0) {
            return hours + (hours == 1 ? " hour" : " hours") + " ago";
        } else if (minutes > 0) {
            return minutes + (minutes == 1 ? " minute" : " minutes") + " ago";
        } else {
            return seconds + (seconds == 1 ? " second" : " seconds") + " ago";
        }
    }


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
        if(inputParts.length < 2) {
            System.out.println("Invalid input. Usage: showNewsFeed strategy");
            return;
        }
        PostDTO postDTO = new PostDTO();
        List< Post> posts = new ArrayList<>();
        if(inputParts[1].equals("commentcount")) {
            postDTO.setShowNewsFeedStrategy(new SortByCommentCount());
            posts = postService.showNewsFeed(postDTO);
        } else if (inputParts[1].equals("followedusers")) {
            postDTO.setShowNewsFeedStrategy(new SortByFollowedUsers());
            posts =postService.showNewsFeed(postDTO);
        } else if (inputParts[1].equals("score")) {
            postDTO.setShowNewsFeedStrategy(new SortByScore());
            posts = postService.showNewsFeed(postDTO);
        } else if (inputParts[1].equals("timestamp")) {
            postDTO.setShowNewsFeedStrategy(new SortByTimeStamp());
            posts = postService.showNewsFeed(postDTO);
        }else {
            System.out.println("Invalid input. Usage: showNewsFeed strategy. Pick from commentCount, followedUsers, score, timestamp");
            return;
        }

        for(Post post : posts) {
            System.out.println(post.getAuthor().getName() + "said: " + post.getContent()  + " " + formatTimeDifference(post.getCreationTime()) );
        }
    }

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
        postService.replyToComment(postDTO);

    }

    public void voteOnComment(String[] userInput) {
    }

    public void experiment(String[] userInput) {
//        User user = Session.getSession().getUser();
//        System.out.println(user.getFollowing());
    }
}
