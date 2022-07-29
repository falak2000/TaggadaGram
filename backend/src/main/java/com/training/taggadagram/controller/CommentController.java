package com.training.taggadagram.controller;

import com.training.taggadagram.Entities.Comment;
//import com.training.taggadagram.Entities.CommentPostRequest;
import com.training.taggadagram.Entities.CommentResponse;
import com.training.taggadagram.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;


    @PostMapping(value = "/insertcomment",consumes = "application/json")
    public ResponseEntity<CommentResponse> saveSocialUser(@RequestBody Comment comment){
        String inputPostId = comment.getPostId();
        return new ResponseEntity<CommentResponse>(commentService.insertComment(comment, inputPostId), HttpStatus.OK);
    }

    @GetMapping(value="/getcomments/{inputPostId}",produces="application/json")
    public ResponseEntity<CommentResponse> getComments(@PathVariable String inputPostId) {
        return new ResponseEntity<CommentResponse>(commentService.getComments(inputPostId), HttpStatus.OK);
    }
}
