package com.newsfeed.repositories;

import com.newsfeed.models.FollowerFollowing;
import com.newsfeed.models.FollowerFollowingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerFollowingRepository extends JpaRepository<FollowerFollowing, FollowerFollowingId> {
}
