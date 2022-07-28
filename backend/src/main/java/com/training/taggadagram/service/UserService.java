package com.training.taggadagram.service;


import com.training.taggadagram.Entities.*;
import com.training.taggadagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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
            loginResponse.setUserSign(user);
        }else if(user.getPassword().equals(BCrypt.hashpw(loginRequest.getPassword(),salt))){
            //user.getPassowrd() returns hashvalue
            loginResponse.setStatus(true);
            loginResponse.setMessage("Logged in");

            //String rand=randomString();
            UUID uuid= UUID.randomUUID();
            user.setRandomString(uuid.toString());

            loginResponse.setUserSign(user);
            userRepository.save(user);

        }else{
            loginResponse.setStatus(false);
            loginResponse.setMessage("Failed");
            loginResponse.setUserSign(null);
        }
        return loginResponse;
    }


    public LogoutResponse logout(UserSign userSign){//uss time user ka information kaise retrieve karein
        UserSign user=userRepository.findByEmail(userSign.getEmail());
        LogoutResponse logoutResponse=new LogoutResponse();

        if(user==null){//user doesn't exists in DB
            logoutResponse.setMessage("User not found");
            logoutResponse.setStatus(false);
        }else if(user.getRandomString()!=null){
            //delete the randomString(token) from database
            user.setRandomString(null);

            userRepository.save(user); //overwrite in DB

            logoutResponse.setMessage("User Logged Out successfully,deleted the token");
            logoutResponse.setStatus(true);
            logoutResponse.setUserSign(user);
        }else{
            logoutResponse.setMessage("User is not logged in");
            logoutResponse.setStatus(false);
        }
        return logoutResponse;
    }
    public StatusMsgResponse followUser(DoubleIdObject doubleIdObject){

        //id1: followed user
        // id2: following user

        // 2 follow kar rha 1 ko, 2 ke following me 1,
        StatusMsgResponse statusMsgResponse = new StatusMsgResponse();
        UserSign followedUser=userRepository.findById(doubleIdObject.getId1());
        UserSign follower=userRepository.findById(doubleIdObject.getId2());

        if(follower==null || followedUser==null){
            statusMsgResponse.setMsg("follow unsuccessfull , either one of user is not present");
            statusMsgResponse.setStatus(false);
            return statusMsgResponse;
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

            statusMsgResponse.setMsg("FOLLOWER - FOLLOWING SAVED SUCCESSFULLY");
            statusMsgResponse.setStatus(true);

            return statusMsgResponse ;
        }

    }

    public StatusMsgResponse unfollowUser(DoubleIdObject doubleIdObject){
        StatusMsgResponse statusMsgResponse = new StatusMsgResponse();
        UserSign followedUser=userRepository.findById(doubleIdObject.getId1());
        UserSign follower=userRepository.findById(doubleIdObject.getId2());

        if(follower==null || followedUser==null){
            statusMsgResponse.setMsg("unfollow unsuccessfull , either one of user is not present");
            statusMsgResponse.setStatus(false);
            return statusMsgResponse;
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

            statusMsgResponse.setMsg("unfollow successfull");
            statusMsgResponse.setStatus(true);
            return statusMsgResponse;
        }
    }
    public List<UserSign> getFollowers(String id){
        UserSign userSign=userRepository.findById(id);
        List<UserSign> listOfFollowers=new ArrayList<>();

        if(userSign==null){
            return new ArrayList<>();
        }else{
            List<String>followers=userSign.getListFollowers();
            if(followers==null){
                return new ArrayList<>();
            }else{

                String ans="";
                for(String follower:followers){ //follower is ID here
                    UserSign temp=userRepository.findById(follower);
                    listOfFollowers.add(temp);
                }

                return listOfFollowers;
            }
        }
    }

    public List<UserSign> getFollowing(String id){
        UserSign userSign=userRepository.findById(id);
        List<UserSign> listOfFollowing=new ArrayList<>();

        if(userSign==null){
            return new ArrayList<>();
        }else{
            List<String>following=userSign.getListFollowing();
            if(following==null){
                return new ArrayList<>();
            }else{
                String fans="";
                for(String follow:following){
                    UserSign temp=userRepository.findById(follow);
                    listOfFollowing.add(temp);

                }


            }

        }
        return listOfFollowing;
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
