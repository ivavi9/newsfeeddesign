package com.newsfeed.services;

import com.newsfeed.dtos.PostDTO;
import com.newsfeed.models.*;
import com.newsfeed.repositories.CommentRepository;
import com.newsfeed.repositories.PostRepository;
import com.newsfeed.repositories.VoteRepository;
import com.newsfeed.singletons.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * A service class that handles operations related to posts such as creating, replying to, and voting on posts.
 * It also includes a method to display the user's newsfeed based on the posts from users they follow.
 */
@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    CommentRepository commentRepository;


    /**
     * Creates a new post with the provided content and current user as the author.
     *
     * postDTO Data transfer object containing the post content.
     */
    public void createPost(PostDTO postDTO) {
        Post post = new Post();
        post.setContent(postDTO.getReplyText());
        post.setAuthor(Session.getSession().getUser());
        postRepository.save(post);
        System.out.println("Post created successfully");
    }

    /**
     * Replies to an existing post by creating a new comment.
     *
     * postDTO Data transfer object containing the post ID and reply content.
     */
    public void replyToPost(PostDTO postDTO) {

        // Find the post by ID
        Optional<Post> optionalPost = postRepository.findById(postDTO.getPostId());
        if (!optionalPost.isPresent()) {
            System.out.println("Post not found");
            return;
        }

        Post post = optionalPost.get();
        // Create a new comment with the current user as the commenter and the post as the parent

        Comment comment = new Comment();
        comment.setCommenter(Session.getSession().getUser());
        comment.setPost(post);
        comment.setReplyText(postDTO.getReplyText());
        commentRepository.save(comment);
    }


    /**
     * Votes on a post, either upvoting or downvoting.
     *
     * postDTO Data transfer object containing the post ID and the vote type.
     */
    public void voteOnPost(PostDTO postDTO) {

        // Find the post by ID
        Optional<Post> optionalPost = postRepository.findById(postDTO.getPostId());
        if (!optionalPost.isPresent()) {
            System.out.println("Post not found");
            return;
        }
        Post post = optionalPost.get();

        // Check if the user has already voted on the post
        Optional<PostVote> optionalPostVote = voteRepository.findByPostAndVoter(post, Session.getSession().getUser());

        // If the user has already voted, update the vote type and vote counts
        if(optionalPostVote.isPresent()) {


            PostVote vote = optionalPostVote.get();
            if (vote.getVoteType() == postDTO.getVoteType()) {
                System.out.println("You have already voted on this post in the same way");
            } else {
                if(postDTO.getVoteType() == VoteType.UPVOTE){
                    post.setUpVoteCount(post.getUpVoteCount()+1);
                    post.setDownVoteCount(post.getDownVoteCount()-1);
                }else{
                    post.setUpVoteCount(post.getUpVoteCount()-1);
                    post.setDownVoteCount(post.getDownVoteCount()+1);
                }

                vote.setVoteType(postDTO.getVoteType());
                voteRepository.save(vote);
                postRepository.save(post);
            }
            return;
        }

        // If the user hasn't voted yet, create a new vote and update the vote counts
        PostVote postVote = new PostVote();
        postVote.setPost(post);
        postVote.setVoteType(postDTO.getVoteType());
        postVote.setVoter(Session.getSession().getUser());

        if(postDTO.getVoteType() == VoteType.UPVOTE){
            post.setUpVoteCount(post.getUpVoteCount()+1);
        }else{
            post.setDownVoteCount(post.getDownVoteCount()+1);
        }


        voteRepository.save(postVote);
        postRepository.save(post);


    }

    /**
     * Retrieves and sorts the newsfeed for the current user based on the users they follow.
     *
     * postDTO Data transfer object containing the sorting strategy for the newsfeed.
     * return A list of sorted posts for the user's newsfeed.
     */
    @Transactional
    public List<Post> showNewsFeed(PostDTO postDTO) {
        User currentUserId = Session.getSession().getUser();

        // Find the posts from users that the current user follows
        List<Post> posts = postRepository.findPostsByUserFollowing(currentUserId);

        // Sort the posts based on the provided sorting strategy
        postDTO.getShowNewsFeedStrategy().sort_news(posts);
//        System.out.println(posts);

        // Return the sorted posts
        return posts;

    }

}
