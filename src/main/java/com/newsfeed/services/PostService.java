package com.newsfeed.services;

import com.newsfeed.dtos.PostDTO;
import com.newsfeed.models.Post;
import com.newsfeed.repositories.PostRepository;
import com.newsfeed.singletons.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public void createPost(PostDTO postDTO) {
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setAuthor(Session.getSession().getUser());
        postRepository.save(post);
        System.out.println("Post created successfully");
    }
}
