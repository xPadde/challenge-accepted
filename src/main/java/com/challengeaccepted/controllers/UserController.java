package com.challengeaccepted.controllers;

import com.challengeaccepted.loggers.HerokuLogger;
import com.challengeaccepted.loggers.HerokuLoggerException;
import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) throws HerokuLoggerException {
        userService.saveUserToDatabase(userModel);
        new HerokuLogger().writeToInfoLog("A new user named " + userModel.getFirstName() + " " + userModel.getLastName() + " has been saved to the database");
        return new ResponseEntity<>(userModel, HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserModel> readUser(@PathVariable Long id) {
        UserModel user = userService.getUserFromDatabase(id);
        return getUserModelResponseEntity(user);
    }

    @CrossOrigin
    @RequestMapping(value = "/user/", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody UserModel userModel) {
        userService.updateUserInDatabase(userModel);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/users/", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<UserModel>> readAllUsers() {
        return new ResponseEntity<>(userService.getAllUsersFromDatabase(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/user/find-by-email", method = RequestMethod.GET)
    public ResponseEntity<UserModel> readUserByEmail(String email) throws HerokuLoggerException {
        return new ResponseEntity<>(userService.getUserByEmailFromDatabase(email), HttpStatus.OK);
    }

    private ResponseEntity<UserModel> getUserModelResponseEntity(UserModel user) {
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<UserModel> login(UserModel userModel) {
        // TODO This method will be used when client log in
        // TODO validate user
        return null;
    }

}