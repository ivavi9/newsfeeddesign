package com.newsfeed.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "follower_following")
public class FollowerFollowing {
    @EmbeddedId
    private FollowerFollowingId id;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @MapsId("followingId")
    @JoinColumn(name = "following_id")
    private User following;


}
