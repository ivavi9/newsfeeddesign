package com.newsfeed.strategies.shownewsfeed;

import com.newsfeed.models.Post;

import java.util.List;

public class SortByFollowedUsers implements ShowNewsFeedStrategy{
    @Override
    public void sort_news(List<Post> posts) {
        posts.sort((o1, o2) ->
             o2.getAuthor().getFollowers().size() - o1.getAuthor().getFollowers().size()
        );
        System.out.println("Sorted by followed users count, higher the better");
    }
}
