package com.newsfeed.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter

public class FollowerFollowingId implements Serializable {
    private long followerId;
    private long followingId;


}