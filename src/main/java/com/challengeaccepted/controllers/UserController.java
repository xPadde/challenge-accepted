package com.challengeaccepted.controllers;

import com.challengeaccepted.models.LoginModel;
import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.services.UserService;
import com.challengeaccepted.services.YubicoService;
import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) throws InvalidKeySpecException, NoSuchAlgorithmException {
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
    public ResponseEntity<UserModel> validateLocalLogin(@RequestBody LoginModel loginModel) throws YubicoVerificationException, YubicoValidationFailure, InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        UserModel userModelFromDatabase = userService.getUserByEmailFromDatabase(loginModel.getEmail());
        YubicoService yubicoService = new YubicoService();

        boolean isPasswordValid = userService.validatePassword(userModelFromDatabase.getPassword(), loginModel.getPassword());
        boolean isYubicoResponseValid = yubicoService.getResponse(loginModel.getOtp()).isOk();
        boolean isYubicoKeyValid =  yubicoService.validateYubiKeyID(loginModel.getOtp(), userModelFromDatabase.getYubiKeyID());

        logger.info("Validating user inputs");
        if (isPasswordValid && isYubicoResponseValid && isYubicoKeyValid) {
            logger.info("Password and Yubico successfully validated");
            userService.validateSpringAccount(userModelFromDatabase); // Send user to Spring Security for validation
            return new ResponseEntity<>(userModelFromDatabase, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private ResponseEntity<UserModel> getUserModelResponseEntity(UserModel user) {
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}