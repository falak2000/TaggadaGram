package com.training.taggadagram.repository;

import com.training.taggadagram.Entities.AuthenticationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthenticationRepository  extends MongoRepository<AuthenticationEntity,String> {

}
