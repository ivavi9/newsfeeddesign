package com.newsfeed.repositories;

import com.newsfeed.models.FollowerFollowing;
import com.newsfeed.models.FollowerFollowingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


// This Repository handles the database operations related to followers and followings
public interface FollowerFollowingRepository extends JpaRepository<FollowerFollowing, FollowerFollowingId> {

    // Custom query to check if a user is already following another user
    @Query("SELECT COUNT(ff) > 0 FROM FollowerFollowing ff WHERE ff.follower.id = :followerId AND ff.following.id = :followingId")
    boolean existsByFollowerIdAndFollowingId(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

}
