package com.newsfeed.strategies;

import com.newsfeed.models.Post;

import java.util.List;

public interface ShowNewsFeedStrategy {
    public void sort_news(List<Post> posts);
}
