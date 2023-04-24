package com.newsfeed.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class User extends BaseModel{

    private String name;
    private String email;
    private String hashedPassword;


    @OneToMany(mappedBy = "author")
    private List<Post> posts;

//    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<FollowerFollowing> following = new HashSet<>();
//
//    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<FollowerFollowing> followers = new HashSet<>();

}
