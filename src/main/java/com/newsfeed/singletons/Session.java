package com.newsfeed.singletons;

import com.newsfeed.models.User;

import java.util.Date;

public class Session{

    private static Session session;
    private User user;
    private Date loginTime;

    private  Session(){}

    public static Session getSession(){

        if(session == null){

            synchronized(Session.class){
                if(session == null){
                    session = new Session();
//                    System.out.println("A new session was created");
                }

            }


        }
        return session;

    }

    public void removeActiveUser(){
        user = null;
    }
    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }

    static {
        Session session = Session.getSession();


    }


}



