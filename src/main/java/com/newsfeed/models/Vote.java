package com.newsfeed.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Vote extends BaseModel{
    private User voter;
    private VoteType voteType;


}
