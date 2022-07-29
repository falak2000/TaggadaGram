package com.training.taggadagram.service;

import com.training.taggadagram.Entities.Comment;
import com.training.taggadagram.Entities.CommentResponse;
import com.training.taggadagram.Entities.Post;
import com.training.taggadagram.Entities.UserSign;
import com.training.taggadagram.repository.CommentRepository;
import com.training.taggadagram.repository.PostRepository;
import com.training.taggadagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    public CommentResponse insertComment(Comment comment, String inputPostId){
        CommentResponse commentResponse = new CommentResponse();
        Optional<Post> post = postRepository.findById(inputPostId);
        if(!post.isPresent()) {
            commentResponse.setStatus("fail");
            commentResponse.setMessage("cannot find target post id: " + inputPostId);
            commentResponse.setPayload(null);
            return commentResponse;
        } else {
            comment.setCreatedAt(Instant.now());
            UserSign userSign = userRepository.findById(comment.getUserId());
            comment.setUserFullName(userSign.getFirstname()+ " "+ userSign.getLastname());
            commentRepository.save(comment);
            Post tempPost = post.get();

            List<String>commentIdList = tempPost.getCommentId();

            if (commentIdList==null) {
                commentIdList = new ArrayList<>();
            }
            commentIdList.add(comment.getId());
            tempPost.setCommentId(commentIdList);
            postRepository.save(tempPost);
            commentResponse.setStatus("success");
            commentResponse.setMessage("success");
            commentResponse.setPayload(comment);
            return commentResponse;
        }
    }
    public CommentResponse getComments(String inputPostId){
        CommentResponse commentResponse = new CommentResponse();
        Optional<Post> optTargetPost = postRepository.findById(inputPostId);
        if (!optTargetPost.isPresent()) {
            commentResponse.setStatus("fail");
            commentResponse.setMessage("fail");
            commentResponse.setPayload(null);
            return commentResponse;
        } else {
            Post targetPost = optTargetPost.get();
            List<String> commentList = targetPost.getCommentId();
            List<Optional<Comment>> commentsListFinal = new ArrayList<>();
            for (int i = 0; i < commentList.size(); i++) {
                commentsListFinal.add(commentRepository.findById(commentList.get(i)))  ;
            }
            if (commentList.size() > 0) {
                commentResponse.setStatus("success");
                commentResponse.setMessage("success");
                commentResponse.setPayload(commentsListFinal);
                return commentResponse;
            } else {
                commentResponse.setStatus("success");
                commentResponse.setMessage("Post id " + inputPostId + " does not have any comment");
                commentResponse.setPayload(null);
                return commentResponse;
            }
        }
    }


}
