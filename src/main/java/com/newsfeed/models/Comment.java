package com.newsfeed.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
public class Comment extends BaseModel{
    private String replyText;

    @ManyToOne
    private User commenter;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Comment parentComment;
    private int upVoteCount;
    private int downVoteCount;


}
