package com.challengeaccepted.controllers;

import com.challengeaccepted.models.LoginModel;
import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.services.UserService;
import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private final static Logger logger = Logger.getLogger(ChallengeController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
        userService.saveUserToDatabase(userModel);
        logger.info("A new user named " + userModel.getFirstName() + " " + userModel.getLastName() + " has been saved to the database");
        return new ResponseEntity<>(userModel, HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserModel> readUser(@PathVariable Long id) {
        UserModel user = userService.getUserFromDatabase(id);
        return getUserModelResponseEntity(user);
    }

    @CrossOrigin
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody UserModel userModel) {
        userService.updateUserInDatabase(userModel);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<UserModel>> readAllUsers() {
        return new ResponseEntity<>(userService.getAllUsersFromDatabase(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/users/find-by-email", method = RequestMethod.GET)
    public ResponseEntity<UserModel> readUserByEmail(String email) throws Exception {
        return new ResponseEntity<>(userService.getUserByEmailFromDatabase(email), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ResponseEntity<UserModel> validateLocalLogin(@RequestBody LoginModel loginModel) throws YubicoVerificationException, YubicoValidationFailure {
        YubicoClient client = YubicoClient.getClient(30796, "QXjIQVpZ0CYk21GxaLpZmdoVsxc=");
        UserModel userModel = userService.getUserByEmailFromDatabase(loginModel.getEmail());
        if(userModel.getPassword().equals(loginModel.getPassword())) {
            VerificationResponse response = client.verify(loginModel.getOtp());
            if (response.isOk()) {
                if (YubicoClient.getPublicId(loginModel.getOtp()).equals(userModel.getYubiKeyID())) {
                    return new ResponseEntity<>(userModel, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
        //
        // TODO This method will be used when client log in
        // TODO validate user
        return null;
    }

}