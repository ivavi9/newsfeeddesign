package com.newsfeed.repositories;

import com.newsfeed.models.Post;
import com.newsfeed.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT p FROM Post p JOIN p.author a JOIN a.followers f WHERE f.follower = :currentUser")
    List<Post> findPostsByUserFollowing(@Param("currentUser") User currentUser);



}
