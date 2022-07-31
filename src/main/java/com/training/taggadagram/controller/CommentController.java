package com.training.taggadagram.controller;

import com.training.taggadagram.Entities.Comment;
//import com.training.taggadagram.Entities.CommentPostRequest;
import com.training.taggadagram.Entities.CommentResponse;
import com.training.taggadagram.service.CommentService;
import com.training.taggadagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.PrimitiveIterator;

@RestController
public class CommentController {

    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

//    @Value("${cross.origin.url}")
//    public final String crossOriginURL;
//    @PostMapping(value = "/insertcomment",consumes = "application/json")
//    public ResponseEntity<CommentResponse> saveSocialUser(@RequestBody Comment comment){
//        String inputPostId = comment.getPostId();
//        return new ResponseEntity<CommentResponse>(commentService.insertComment(comment, inputPostId), HttpStatus.OK);
//    }

    @PostMapping(value = "/insertcomment",consumes = "application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<CommentResponse> saveSocialUser(@RequestBody Comment comment,@RequestHeader("token") String headerString){
        if(userService.isValideUser(headerString)){
            String inputPostId = comment.getPostId();
            return new ResponseEntity<CommentResponse>(commentService.insertComment(comment, inputPostId), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<CommentResponse>(new CommentResponse(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value="/getcomments/{inputPostId}",produces="application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<CommentResponse> getComments(@PathVariable String inputPostId,@RequestHeader("token") String headerString) {
        if(userService.isValideUser(headerString)){
            return new ResponseEntity<CommentResponse>(commentService.getComments(inputPostId), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<CommentResponse>(new CommentResponse(), HttpStatus.NOT_FOUND);
        }

    }
}
