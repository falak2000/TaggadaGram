package com.training.taggadagram.repository;

import com.training.taggadagram.Entities.UserSign;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserSign,Long> {
    UserSign findByEmail(String email);
    UserSign findById (String id);
}
