package com.training.taggadagram.repository;

import com.training.taggadagram.Entities.UserSign;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<UserSign,Long> {
    UserSign findByEmail(String email);

    UserSign findById (String id);


    // searching user with username starting with id ;
    @Query(value="{id : {$regex : ?0}}")
    List<UserSign>getUsers(String id);

}
