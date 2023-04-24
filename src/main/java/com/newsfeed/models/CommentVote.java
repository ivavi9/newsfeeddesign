package com.newsfeed.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class CommentVote extends Vote{

    @ManyToOne
    private Comment comment;
}
