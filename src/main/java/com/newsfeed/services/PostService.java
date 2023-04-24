package com.newsfeed.services;

import com.newsfeed.dtos.PostDTO;
import com.newsfeed.models.Comment;
import com.newsfeed.models.Post;
import com.newsfeed.repositories.CommentRepository;
import com.newsfeed.repositories.PostRepository;
import com.newsfeed.singletons.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

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
}
