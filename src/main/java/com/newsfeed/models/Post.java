package com.newsfeed.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class Post extends BaseModel{
    private String content;

    @ManyToOne
    private User author;
    private int upVoteCount;
    private int downVoteCount;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

}
