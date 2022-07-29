package com.training.taggadagram.controller;

import com.training.taggadagram.Entities.*;
import com.training.taggadagram.repository.UserRepository;
import com.training.taggadagram.service.UserService;
// import jdk.vm.ci.code.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<PasswordUpdateStatus> newPassword(@RequestBody PasswordUpdateEntity passwordUpdateEntity,@RequestHeader("token") String headerString){
        if(userService.isValideUser(headerString)){
            PasswordUpdateStatus passwordUpdateStatus = userService.updatePassword(passwordUpdateEntity);
            return new ResponseEntity<PasswordUpdateStatus>(passwordUpdateStatus, HttpStatus.OK);
        }else
            return new ResponseEntity<PasswordUpdateStatus>(new PasswordUpdateStatus(),HttpStatus.NOT_FOUND);
    }


    @PostMapping(value="/userlogout",consumes = "application/json",produces = "application/json")
    public ResponseEntity<LogoutResponse> logout(@RequestBody UserSign userSign,@RequestHeader("token") String headerString){
        if(userService.isValideUser(headerString)){
            return new ResponseEntity<LogoutResponse>(userService.logout(userSign), HttpStatus.OK);
        }else
            return new ResponseEntity<LogoutResponse>(new LogoutResponse(),HttpStatus.NOT_FOUND);
    }


    @PostMapping(value = "/follow")
    public ResponseEntity<StatusMsgResponse> follow(@RequestBody DoubleIdObject doubleIdObject,@RequestHeader("token") String headerString){
        if(userService.isValideUser(headerString)){
            StatusMsgResponse statusMsgResponse = userService.followUser(doubleIdObject);
            return new ResponseEntity<StatusMsgResponse>(statusMsgResponse, HttpStatus.OK);
        }else
            return new ResponseEntity<StatusMsgResponse>(new StatusMsgResponse(),HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/unfollow")
    public ResponseEntity<StatusMsgResponse> unfollow(@RequestBody DoubleIdObject doubleIdObject,@RequestHeader("token") String headerString){
        if(userService.isValideUser(headerString)){
            StatusMsgResponse statusMsgResponse = userService.unfollowUser(doubleIdObject);
            return new ResponseEntity<StatusMsgResponse>(statusMsgResponse, HttpStatus.OK);
        }else
            return new ResponseEntity<StatusMsgResponse>(new StatusMsgResponse(), HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/followers/{id}")
    public ResponseEntity<List<UserSign>> getFollowers(@PathVariable String id,@RequestHeader("token") String headerString){
        if(userService.isValideUser(headerString)){
            return new ResponseEntity<List<UserSign>>(userService.getFollowers(id), HttpStatus.OK);
        }else
            return new ResponseEntity<List<UserSign>>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/following/{id}")
    public ResponseEntity<List<UserSign>> getFollowing(@PathVariable String id,@RequestHeader("token") String headerString){
        if(userService.isValideUser(headerString)){
            return new ResponseEntity<List<UserSign>>(userService.getFollowing(id), HttpStatus.OK);
        }else
            return new ResponseEntity<List<UserSign>>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }

}
