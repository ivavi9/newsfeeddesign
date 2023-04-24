# Newsfeed Application

The Newsfeed Application is a simple command-line based social media application where users can create accounts, follow other users, create posts, reply to posts, and upvote or downvote posts and comments. The application also provides a way to view a user's newsfeed, which is sorted based on different strategies such as comment count, timestamp, followed users, and score.
Getting Started

These instructions will help you get started with the application and understand its functionalities.
Prerequisites

Make sure you have Java and the required dependencies installed to run the application.
Running the Application

Once you have the application running, you can use the following example commands to interact with it and build your own:

## Signup

To create a new user account, use the signup command followed by a username, email, and password:

```
signup avi avi@gmail.com password
signup aarush aarush@yahoo.com password2
signup aviral aviral@hotmail.com password3
```

## Login

To log in as a user, use the login command followed by the email and password:

```
login avi@gmail.com password
login aarush@yahoo.com password2
login aviral@hotmail.com password3

```

## Follow

To follow another user, use the follow command followed by the user's email:

```
follow aarush@yahoo.com
follow avi@gmail.com
follow aviral@hotmail.com


```

## Create a Post

To create a new post, use the post command followed by the post content:

```
post I have been wondering what is this all about
post another post

```

## Reply to a Post

To reply to an existing post, use the reply command followed by the post ID and the reply text:

```agsl
reply 1 Wow wow wow

```

## Upvote or Downvote a Post

To upvote or downvote a post, use the upvote or downvote command followed by the post ID:

```agsl
upvote 1

```

## Reply to a Comment

To reply to an existing comment, use the replycomment command followed by the comment ID and the reply text:

```agsl
replycomment 1 it is a reply to comment itself

```
## Upvote or Downvote a Comment

To upvote or downvote a comment, use the upvotecomment or downvotecomment command followed by the comment ID:

```agsl
downvotecomment 1

```

## Show Newsfeed

To display the news feed sorted based on different strategies, use the shownewsfeed command followed by the sorting strategy:

```agsl
shownewsfeed commentcount
shownewsfeed timestamp
shownewsfeed followedusers
shownewsfeed score

```

## Exit

To exit the application, simply use the exit command:
```agsl
exit

```

