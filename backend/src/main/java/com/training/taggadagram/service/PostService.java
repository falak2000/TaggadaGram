package com.training.taggadagram.service;

import com.training.taggadagram.Entities.*;
import com.training.taggadagram.repository.PostRepository;
import com.training.taggadagram.repository.UserRepository;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public PostResponse addPost(Post post){
        PostResponse postResponse = new PostResponse();
        postRepository.save(post);
        postResponse.setMsg("Post added Successfully");
        postResponse.setStatus(true);
     return postResponse;
    }

    public Optional<List<Post>> allMyPost(String userId){
        Optional<List<Post>> postList = postRepository.findAllByUserId(userId);
        return postList;
    }

    //need to verify if the liker is in the database or not (unverified person should not verify it).
    //one user should not be allowed to like multiple times.
    public LikeResponse likePost(IdUserPost idUserPost){
        LikeResponse likeResponse = new LikeResponse();
        Post post = postRepository.findByPostId(idUserPost.getPostId());
        Optional<List<String>> optListLikes = Optional.ofNullable(post.getLikesList());
        if(optListLikes.isPresent()){
            if(optListLikes.get().contains(idUserPost.getUserId())){
                optListLikes.get().remove(idUserPost.getUserId());
                likeResponse.setMsg("unlike the Post");
            }else {
                optListLikes.get().add(idUserPost.getUserId());
                likeResponse.setMsg("Liked the Post");
            }
        }else{
            post.setLikesList(Arrays.asList(idUserPost.getUserId()));
            likeResponse.setMsg("Liked the Post");
        }
        postRepository.save(post);

        likeResponse.setStatus(true);
        return likeResponse;
    }

    public List<Post> getNewsFeed(String userId){
        UserSign user = userRepository.findById(userId);
//        List<Post> listPost = postRepository.findAllPostOfFollowing(user.getListFollowing());
        List<Post> listPost = new ArrayList<Post>();
        for(String follow : user.getListFollowing()){
           Optional<List<Post>> postlist = postRepository.findAllByUserId(follow);
           if(postlist.isPresent())
           listPost.addAll(postlist.get());
        }
        return listPost;
    }

}
