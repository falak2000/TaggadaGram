package com.training.taggadagram.controller;

import com.training.taggadagram.Entities.IdUserPost;
import com.training.taggadagram.Entities.LikeResponse;
import com.training.taggadagram.Entities.Post;
import com.training.taggadagram.Entities.PostResponse;
import com.training.taggadagram.service.PostService;
import com.training.taggadagram.service.UserService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @PostMapping(value="/addpost",consumes="application/json",produces="application/json")
    public ResponseEntity<Post> addPost(@RequestBody Post post,@RequestHeader("token") String headerString){
        if(userService.isValideUser(headerString)){
            Post postResponse = postService.addPost(post);
            return new ResponseEntity<Post>(postResponse, HttpStatus.OK);
        }else
            return new ResponseEntity<Post>(new Post(),HttpStatus.NOT_FOUND);
    }

    @GetMapping(value="/myposts/{userId}",produces="application/json")
    public ResponseEntity<List<Post>> myPost(@PathVariable String userId,@RequestHeader("token") String headerString){
        if(userService.isValideUser(headerString)){
            Optional<List<Post>> listPost = postService.allMyPost(userId);
            if (listPost.isPresent())
                return new ResponseEntity<List<Post>>(listPost.get(), HttpStatus.OK);
            else
                return new ResponseEntity<List<Post>>(listPost.get(), HttpStatus.NOT_FOUND);
        }else
            return new ResponseEntity<List<Post>>(new ArrayList<Post>(),HttpStatus.NOT_FOUND);
    }

    @PostMapping(value="/likepost",consumes = "application/json",produces = "application/json")
    public ResponseEntity<LikeResponse> likePost(@RequestBody IdUserPost idUserPost,@RequestHeader("token") String headerString ){
        if(userService.isValideUser(headerString)){
            LikeResponse likeResponse = postService.likePost(idUserPost);
            return new ResponseEntity<LikeResponse>(likeResponse, HttpStatus.OK);
        }else
            return new ResponseEntity<LikeResponse>(new LikeResponse(),HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/getnewsfeed/{userId}",produces = "application/json")
    public ResponseEntity<List<Post>> getNewsFeed(@PathVariable String userId,@RequestHeader("token") String headerString){
        if(userService.isValideUser(headerString)){
            Optional<List<Post>> postList = postService.getNewsFeed(userId);
            if (postList.isPresent())
                return new ResponseEntity<List<Post>>(postList.get(), HttpStatus.OK);
            else {
                Post dummyPost = new Post();
                dummyPost.setPostId("0");
                dummyPost.setContent("follow Mudiji");
                dummyPost.setImage("");
                dummyPost.setCreated_at(LocalDateTime.now());
                dummyPost.setUserId(userId);
                List<Post> dummmyPostList = new ArrayList<>();
                dummmyPostList.add(dummyPost);
                return new ResponseEntity<List<Post>>(dummmyPostList, HttpStatus.OK);
            }
        }else
            return new ResponseEntity<List<Post>>(new ArrayList<>(),HttpStatus.NOT_FOUND);
    }

}
