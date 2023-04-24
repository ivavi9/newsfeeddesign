package com.newsfeed.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class Vote extends BaseModel{

    @ManyToOne
    private User voter;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;


}
