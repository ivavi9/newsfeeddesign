package com.newsfeed.repositories;

import com.newsfeed.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<PostVote> findByPostAndVoter(Post post, User user);

    Optional<CommentVote> findByCommentAndVoter(Comment comment, User user);
}
