package com.challengeaccepted.controllers;

import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.services.UserService;
import org.hibernate.annotations.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody UserModel userModel) {

        UserModel tempUserModel = readUserByEmail(userModel.getEmail());

        if(tempUserModel == null || tempUserModel.getEmail().equals("")){
            userService.saveUserToDatabase(userModel);
            System.out.println("nu fanns den inte");
        }else{
            tempUserModel.setFirstName(userModel.getFirstName());
            tempUserModel.setLastName(userModel.getLastName());
            updateUser(tempUserModel);
            System.out.println("nu fanns den");
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserModel> readUser(@PathVariable Long id) {
        return new ResponseEntity<UserModel>(userService.getUserFromDatabase(id), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody UserModel userModel) {
        userService.updateUserInDatabase(userModel);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/users/", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<UserModel>> readAllUsers() {
        return new ResponseEntity<ArrayList<UserModel>>(userService.getAllUsersFromDatabase(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/user/googletokenid/{googleTokenId}", method = RequestMethod.GET)
    public ResponseEntity<UserModel> readUserByGoogleTokenId(@PathVariable String googleTokenId) {
        return new ResponseEntity<UserModel>(userService.getUserByGoogleTokenIdFromDatabase(googleTokenId), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/user/email/{email}", method = RequestMethod.GET)
    public UserModel readUserByEmail(@PathVariable String email){
        return userService.getUserEmailFromDatabase(email);
    }

}