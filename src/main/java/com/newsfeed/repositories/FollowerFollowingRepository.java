package com.newsfeed.repositories;

import com.newsfeed.models.FollowerFollowing;
import com.newsfeed.models.FollowerFollowingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowerFollowingRepository extends JpaRepository<FollowerFollowing, FollowerFollowingId> {

    @Query("SELECT COUNT(ff) > 0 FROM FollowerFollowing ff WHERE ff.follower.id = :followerId AND ff.following.id = :followingId")
    boolean existsByFollowerIdAndFollowingId(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

}
