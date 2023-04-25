package com.newsfeed.services;

import com.newsfeed.dtos.CommentDTO;
import com.newsfeed.models.Comment;
import com.newsfeed.models.CommentVote;
import com.newsfeed.models.Post;
import com.newsfeed.models.VoteType;
import com.newsfeed.repositories.CommentRepository;
import com.newsfeed.repositories.VoteRepository;
import com.newsfeed.singletons.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * A service class that handles actions related to comments, including replying to comments and voting on comments.
 */
@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    VoteRepository voteRepository;


    /**
     * Replies to an existing comment by creating a new comment.
     *
     * postDTO Data transfer object containing the comment ID and reply content.
     */
    public void replyToComment(CommentDTO postDTO) {

        // Find the comment by ID
        Optional<Comment> optionalComment = commentRepository.findById(postDTO.getCommentId());
        if (!optionalComment.isPresent()) {
            System.out.println("Comment not found");
            return;
        }

        Comment comment = optionalComment.get();

        // Get the parent post of the comment
        Post post = comment.getPost();

        // Create a new comment with the current user as the commenter, the post as the parent, and the original comment as the parent comment
        Comment reply = new Comment();
        reply.setCommenter(Session.getSession().getUser());
        reply.setPost(post);
        reply.setReplyText(postDTO.getReplyText());
        reply.setParentComment(comment);
        commentRepository.save(reply);
    }

    /**
     * Votes on a comment, either upvoting or downvoting.
     *
     * commentDTO Data transfer object containing the comment ID and the vote type.
     */
    public void voteOnComment(CommentDTO commentDTO) {

        // Find the comment by ID
        Optional<Comment> optionalComment = commentRepository.findById(commentDTO.getCommentId());
        if (!optionalComment.isPresent()) {
            System.out.println("Comment not found");
            return;
        }
        Comment comment = optionalComment.get();

        // Check if the user has already voted on the comment
        Optional<CommentVote> optionalCommentVote = voteRepository.findByCommentAndVoter(comment, Session.getSession().getUser());

        // If the user has already voted, update the vote type and vote counts
        if(optionalCommentVote.isPresent()) {
            CommentVote vote = optionalCommentVote.get();
            if (vote.getVoteType() == commentDTO.getVoteType()) {
                System.out.println("You have already voted on this comment in the same way");
            } else {
                if (commentDTO.getVoteType() == VoteType.UPVOTE) {
                    comment.setUpVoteCount(comment.getUpVoteCount() + 1);
                    comment.setDownVoteCount(comment.getDownVoteCount() - 1);
                } else {
                    comment.setUpVoteCount(comment.getUpVoteCount() - 1);
                    comment.setDownVoteCount(comment.getDownVoteCount() + 1);

                }
                vote.setVoteType(commentDTO.getVoteType());
                voteRepository.save(vote);
                commentRepository.save(comment);
            }
            return;
        }

        // If the user hasn't voted yet, create a new vote and update the vote counts
        CommentVote commentVote = new CommentVote();
        commentVote.setComment(comment);
        commentVote.setVoteType(commentDTO.getVoteType());
        commentVote.setVoter(Session.getSession().getUser());
        if (commentDTO.getVoteType() == VoteType.UPVOTE) {
            comment.setUpVoteCount(comment.getUpVoteCount() + 1);
        }else{
            comment.setDownVoteCount(comment.getDownVoteCount()+1);
        }
        voteRepository.save(commentVote);
        commentRepository.save(comment);
//        System.out.println("here");
    }
}
