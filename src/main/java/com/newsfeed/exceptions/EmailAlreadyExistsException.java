package com.newsfeed.exceptions;

public class EmailAlreadyExistsException extends Exception{
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
