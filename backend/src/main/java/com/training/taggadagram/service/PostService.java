package com.training.taggadagram.service;

import com.training.taggadagram.Entities.Post;
import com.training.taggadagram.Entities.PostResponse;
import com.training.taggadagram.Entities.User;
import com.training.taggadagram.repository.PostRepository;
import com.training.taggadagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

}
