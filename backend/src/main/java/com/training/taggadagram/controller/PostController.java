package com.training.taggadagram.controller;

import com.training.taggadagram.Entities.IdUserPost;
import com.training.taggadagram.Entities.LikeResponse;
import com.training.taggadagram.Entities.Post;
import com.training.taggadagram.Entities.PostResponse;
import com.training.taggadagram.service.PostService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping(value="/addpost",consumes="application/json",produces="application/json")
    public ResponseEntity<PostResponse> addPost(@RequestBody Post post){
        PostResponse postResponse = postService.addPost(post);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    @GetMapping(value="/myposts/{userId}",produces="application/json")
    public ResponseEntity<List<Post>> myPost(@PathVariable String userId){
        Optional<List<Post>> listPost = postService.allMyPost(userId);
        if(listPost.isPresent())
            return new ResponseEntity<List<Post>>(listPost.get(),HttpStatus.OK);
        else
            return new ResponseEntity<List<Post>>(listPost.get(),HttpStatus.NOT_FOUND);
    }

    @PostMapping(value="/likepost",consumes = "application/json",produces = "application/json")
    public ResponseEntity<LikeResponse> likePost(@RequestBody IdUserPost idUserPost){
        LikeResponse likeResponse = postService.likePost(idUserPost);
        return new ResponseEntity<LikeResponse>(likeResponse,HttpStatus.OK);
    }

    @GetMapping(value = "/getnewsfeed/{userId}",produces = "application/json")
    public ResponseEntity<List<Post>> getNewsFeed(@PathVariable String userId){
        List<Post> postList = postService.getNewsFeed(userId);
        return new ResponseEntity<List<Post>>(postList,HttpStatus.OK);
    }

}
