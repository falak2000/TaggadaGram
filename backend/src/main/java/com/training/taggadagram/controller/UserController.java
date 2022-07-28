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
    public PasswordUpdateStatus newPassword(@RequestBody PasswordUpdateEntity passwordUpdateEntity ){
         PasswordUpdateStatus passwordUpdateStatus = userService.updatePassword(passwordUpdateEntity);
         return passwordUpdateStatus;


    }


    @PostMapping(value = "/follow")
    public String follow(@RequestBody DoubleIdObject doubleIdObject){
        String status=userService.followUser(doubleIdObject);
        if(status.equals("FOLLOWER - FOLLOWING SAVED SUCCESSFULLY"))
            return "SUCCESSFULL followed";
        else return "NOT SUCCESSFULLY";
    }

    @PostMapping(value = "/unfollow")
    public String unfollow(@RequestBody DoubleIdObject doubleIdObject){
        String status= userService.unfollowUser(doubleIdObject);
        if(status.equals("unfollow successfull")){
            return "Unfollow Successfull";
        }
        else return "Unsuccessfull unfollow";
    }
    @GetMapping(value = "/followers/{id}")
    public String getFollowers(@PathVariable String id){
        String status= userService.getFollowers(id);
        if(status.equals("")){
            return "NO RESULT";
        }else{
            return status;
        }
    }

    @GetMapping(value = "/following/{id}")
    public String getFollowing(@PathVariable String id){
        String status= userService.getFollowing(id);
        if(status.equals("")){
            return "NO RESULT";
        }else{
            return status;
        }
    }
    @GetMapping (value= "/searchuser/{id}")
    public List<UserSign> searchUser(@PathVariable  String id){
        List<UserSign> userList = userService.getUser(id) ;
         return userList;
    }
}
