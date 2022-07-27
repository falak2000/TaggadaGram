package com.training.taggadagram.service;


import com.training.taggadagram.Entities.*;
import com.training.taggadagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public RegisterResponse register(UserSign user){
        //first encrypt the password and then store in DB
        UserSign currentUser=userRepository.findByEmail(user.getEmail());
        RegisterResponse registerResponse = new RegisterResponse();

        if(currentUser!=null){
            //user already exists
            registerResponse.setStatus(false);
            registerResponse.setMessage("User Already exists");
            return registerResponse;
        }else{
            //here we generate salt and hashed password using bcrypt
            String salt= BCrypt.gensalt();
            System.out.println(salt);

            String hashedPassword=BCrypt.hashpw(user.getPassword(),salt); //using salt
            user.setPassword(hashedPassword); //storing hashed password
            user.setSalt(salt);//storing salt

            userRepository.save(user); // Storing in database
            registerResponse.setStatus(true);
            registerResponse.setMessage("Signup Successful");
            return registerResponse;

        }

    }

    public LoginResponse authenticate(LoginRequest loginRequest){
        UserSign user = userRepository.findByEmail(loginRequest.getEmail());
        LoginResponse loginResponse = new LoginResponse();
        String salt=user.getSalt();

        if(user == null){
            loginResponse.setStatus(false);
            loginResponse.setMessage("Not valid credentials");
        }else if(user.getPassword().equals(BCrypt.hashpw(loginRequest.getPassword(),salt))){
            //user.getPassowrd() returns hashvalue
            loginResponse.setStatus(true);
            loginResponse.setMessage("Logged in");
        }else{
            loginResponse.setStatus(false);
            loginResponse.setMessage("Failed");
        }
        return loginResponse;
    }
}
