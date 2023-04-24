package com.newsfeed.strategies.shownewsfeed;

import com.newsfeed.models.Post;

import java.util.List;

public class SortByTimeStamp implements ShowNewsFeedStrategy{
    @Override
    public void sort_news(List<Post> posts) {
        posts.sort((o1, o2) -> o2.getCreationTime().compareTo(o1.getCreationTime()));
        System.out.println("Sorted by time stamp");
    }
}
