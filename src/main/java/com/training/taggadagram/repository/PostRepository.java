package com.training.taggadagram.repository;

import com.training.taggadagram.Entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<Post,String> {

    @Query("{userId:?0}")
    public Optional<List<Post>> findAllByUserId(String userId);

    @Query("{postId:?0}")
    public Post findByPostId(String postId);

//  @Query("MATCH (p:post) WHERE p.postId in $ids RETURN as id")

    @Query("{userId:{$nin:?0}}")
    Optional<List<Post>> findAllPostOfFollowing(List<String> following);

}
