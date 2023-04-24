package com.newsfeed.services;

import com.newsfeed.dtos.PostDTO;
import com.newsfeed.models.Comment;
import com.newsfeed.models.Post;
import com.newsfeed.models.PostVote;
import com.newsfeed.models.VoteType;
import com.newsfeed.repositories.CommentRepository;
import com.newsfeed.repositories.PostRepository;
import com.newsfeed.repositories.VoteRepository;
import com.newsfeed.singletons.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    CommentRepository commentRepository;

    public void createPost(PostDTO postDTO) {
        Post post = new Post();
        post.setContent(postDTO.getReplyText());
        post.setAuthor(Session.getSession().getUser());
        postRepository.save(post);
        System.out.println("Post created successfully");
    }

    public void replyToPost(PostDTO postDTO) {

        Optional<Post> optionalPost = postRepository.findById(postDTO.getPostId());
        if (!optionalPost.isPresent()) {
            System.out.println("Post not found");
            return;
        }

        Post post = optionalPost.get();

        Comment comment = new Comment();
        comment.setCommenter(Session.getSession().getUser());
        comment.setPost(post);
        comment.setReplyText(postDTO.getReplyText());
        commentRepository.save(comment);
    }

    public void voteOnPost(PostDTO postDTO) {
        Optional<Post> optionalPost = postRepository.findById(postDTO.getPostId());
        if (!optionalPost.isPresent()) {
            System.out.println("Post not found");
            return;
        }
        Post post = optionalPost.get();

        Optional<PostVote> optionalPostVote = voteRepository.findByPostAndVoter(post, Session.getSession().getUser());

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
}
