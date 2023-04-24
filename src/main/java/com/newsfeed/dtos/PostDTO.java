package com.newsfeed.dtos;

import com.newsfeed.models.VoteType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private long postId;
    private String replyText;
    private VoteType voteType;

}
