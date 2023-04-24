package com.newsfeed.models;

import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class PostVote extends Vote{

    @ManyToOne
    private Post post;


}
