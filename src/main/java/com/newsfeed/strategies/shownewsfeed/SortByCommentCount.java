package com.newsfeed.strategies.shownewsfeed;

import com.newsfeed.models.Post;

import java.util.List;

public class SortByCommentCount implements ShowNewsFeedStrategy{
    @Override
    public void sort_news(List<Post> posts) {

        posts.sort((o1, o2) -> o2.getComments().size() - o1.getComments().size());

        System.out.println("Sorting by comment count");


    }
}
