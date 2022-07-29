package com.training.taggadagram.repository;

import com.training.taggadagram.Entities.UserSign;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<UserSign,Long> {
    UserSign findByEmail(String email);
    UserSign findById(String id);


}
