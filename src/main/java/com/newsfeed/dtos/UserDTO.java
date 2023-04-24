package com.newsfeed.dtos;

import com.newsfeed.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String email;
    private String password;

    private User currentUser;
    private long followUserId;
    private String followUserEmail;

}
