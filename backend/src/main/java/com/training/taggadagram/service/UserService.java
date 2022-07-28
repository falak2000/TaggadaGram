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

            String hashedPassword=BCrypt.hashpw(user.getPassword(),salt); //using salt
            user.setPassword(hashedPassword); //storing hashed password
            user.setSalt(salt);//storing salt
            UserSign userSign = new UserSign(user);
            userRepository.save(userSign); // Storing in database
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
    public PasswordUpdateStatus updatePassword(PasswordUpdateEntity passwordUpdateEntity){
        UserSign user = userRepository.findByEmail(passwordUpdateEntity.getUserEmail());
        PasswordUpdateStatus passwordUpdateStatus= new PasswordUpdateStatus();
        String salt = user.getSalt();
        if(user==null){ // incorrect email address
            passwordUpdateStatus.setStatus(false);
            passwordUpdateStatus.setMessage("Please enter correct email address") ;


        }else if(user.getPassword().equals(BCrypt.hashpw(passwordUpdateEntity.getCurrentPassword(),salt))){
            // updating the current password for the user ;
            user.setPassword(passwordUpdateEntity.getNewPassword());
            String hashedPassword=BCrypt.hashpw(user.getPassword(),salt); //using salt
            user.setPassword(hashedPassword); //storing hashed password
            user.setSalt(salt);//storing  the previous salt
            userRepository.save(user) ;
            passwordUpdateStatus.setStatus(true);
            passwordUpdateStatus.setMessage("PASSWORD UPDATED SUCCESSFULLY");

        }else{
            passwordUpdateStatus.setStatus(false);
            passwordUpdateStatus.setMessage("CURRENT PASSWORD DOESNT MATCHES");
        }
        return passwordUpdateStatus ;

    }


}
