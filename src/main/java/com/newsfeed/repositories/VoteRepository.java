package com.newsfeed.repositories;

import com.newsfeed.models.Post;
import com.newsfeed.models.PostVote;
import com.newsfeed.models.User;
import com.newsfeed.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<PostVote> findByPostAndVoter(Post post, User user);
}
