package com.training.taggadagram.service;


import com.training.taggadagram.Entities.*;
import com.training.taggadagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            //System.out.println(salt);

            String hashedPassword=BCrypt.hashpw(user.getPassword(),salt); //using salt
            user.setPassword(hashedPassword); //storing hashed password
            user.setSalt(salt);//storing salt

            userRepository.save(user); // Storing in database with hashed password
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

    public String followUser(DoubleIdObject doubleIdObject){

        //id1: followed user
        // id2: following user

        // 2 follow kar rha 1 ko, 2 ke following me 1,
        UserSign followedUser=userRepository.findById(doubleIdObject.getId1());
        UserSign follower=userRepository.findById(doubleIdObject.getId2());

        if(follower==null || followedUser==null){


            return "follow unsuccessfull , either one of user is not present";
        }else{
            //adding to follower list


            List<String> followerList = followedUser.getListFollowers();
            if (followerList == null) {
                followerList = new ArrayList<>();
            }
            followerList.add(follower.getId());
            followedUser.setListFollowers(followerList);

            // add to following list
            List<String> followingList = follower.getListFollowing();
            if (followingList == null) {
                followingList = new ArrayList<>();
            }
            followingList.add(followedUser.getId());
            follower.setListFollowing(followingList);

            userRepository.save(followedUser);
            userRepository.save(follower);

            return "FOLLOWER - FOLLOWING SAVED SUCCESSFULLY" ;
        }

    }

    public String unfollowUser(DoubleIdObject doubleIdObject){
        UserSign followedUser=userRepository.findById(doubleIdObject.getId1());
        UserSign follower=userRepository.findById(doubleIdObject.getId2());

        if(follower==null || followedUser==null){


            return "unfollow unsuccessfull , either one of user is not present";
        }else{
            List<String> followerList = followedUser.getListFollowers();
            if (followerList == null) {
                followerList = new ArrayList<>();
            }
            followerList.remove(follower.getId());
            followedUser.setListFollowers(followerList);

            // add to following list
            List<String> followingList = follower.getListFollowing();
            if (followingList == null) {
                followingList = new ArrayList<>();
            }
            followingList.remove(followedUser.getId());
            follower.setListFollowing(followingList);

            userRepository.save(followedUser);
            userRepository.save(follower);

            return "unfollow successfull";
        }
    }
    public String getFollowers(String id){
        UserSign userSign=userRepository.findById(id);
        if(userSign==null){
            return "";
        }else{
            List<String>followers=userSign.getListFollowers();
            if(followers==null){
                return "";
            }else{

                String ans="";
                for(String follower:followers){
                    UserSign temp=userRepository.findById(follower);
                    ans=ans+" "+temp.getFirstname();
                }

                return ans;
            }
        }
    }

    public String getFollowing(String id){
        UserSign userSign=userRepository.findById(id);
        if(userSign==null){
            return "";
        }else{
            List<String>following=userSign.getListFollowing();
            if(following==null){
                return "";
            }else{
                String fans="";
                for(String follow:following){
                    UserSign temp=userRepository.findById(follow);
                    fans=fans+" "+temp.getFirstname();
                }

                return fans;
            }

        }
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
