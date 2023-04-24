package com.newsfeed.strategies.shownewsfeed;

import com.newsfeed.models.Post;

import java.util.List;

public class SortByScore implements ShowNewsFeedStrategy{
    @Override
    public void sort_news(List<Post> posts) {
        posts.sort((p1,p2)->(p2.getUpVoteCount()-p2.getDownVoteCount())-(p1.getUpVoteCount()-p1.getDownVoteCount()));
        System.out.println("Sorted by Score");
    }
}
