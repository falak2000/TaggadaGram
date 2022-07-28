package com.training.taggadagram.controller;

import com.training.taggadagram.Entities.*;
import com.training.taggadagram.repository.UserRepository;
import com.training.taggadagram.service.UserService;
// import jdk.vm.ci.code.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value="/register",consumes = "application/json",produces = "application/json")
    public RegisterResponse register(@RequestBody UserSign user){

        RegisterResponse registerResponse= userService.register(user);
        return registerResponse;
        //userService.register(user);
        //return "FINALLY";

    }

    @PostMapping(value = "/login",consumes = "application/json",produces="application/json")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        try{
            LoginResponse loginResponse = userService.authenticate(loginRequest);
            if(loginResponse.isStatus()){
                return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<LoginResponse>(loginResponse,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            LoginResponse loginResponse = null;
            return new ResponseEntity<LoginResponse>(loginResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value="/updatePassword", consumes = "application/json"  , produces="application/json")
    public ResponseEntity<PasswordUpdateStatus> newPassword(@RequestBody PasswordUpdateEntity passwordUpdateEntity ){
         PasswordUpdateStatus passwordUpdateStatus = userService.updatePassword(passwordUpdateEntity);
         return new ResponseEntity<PasswordUpdateStatus>(passwordUpdateStatus,HttpStatus.OK);


    }


    @PostMapping(value="/userlogout",consumes = "application/json",produces = "application/json")
    public ResponseEntity<LogoutResponse> logout(@RequestBody UserSign userSign){
      return new ResponseEntity<LogoutResponse>(userService.logout(userSign),HttpStatus.OK);
    }


    @PostMapping(value = "/follow")
    public ResponseEntity<StatusMsgResponse> follow(@RequestBody DoubleIdObject doubleIdObject){
        StatusMsgResponse statusMsgResponse=userService.followUser(doubleIdObject);
        return new ResponseEntity<StatusMsgResponse>(statusMsgResponse,HttpStatus.OK);
    }

    @PostMapping(value = "/unfollow")
    public ResponseEntity<StatusMsgResponse> unfollow(@RequestBody DoubleIdObject doubleIdObject){
        StatusMsgResponse statusMsgResponse= userService.unfollowUser(doubleIdObject);
        return new ResponseEntity<StatusMsgResponse>(statusMsgResponse,HttpStatus.OK);
    }
    @GetMapping(value = "/followers/{id}")
    public ResponseEntity<List<UserSign>> getFollowers(@PathVariable String id){
       return new ResponseEntity<List<UserSign>>(userService.getFollowers(id),HttpStatus.OK);
    }

    @GetMapping(value = "/following/{id}")
    public ResponseEntity<List<UserSign>> getFollowing(@PathVariable String id){
       return new ResponseEntity<List<UserSign>>(userService.getFollowing(id),HttpStatus.OK);
       // return
    }

}
