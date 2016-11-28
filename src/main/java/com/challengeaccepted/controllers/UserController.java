package com.challengeaccepted.controllers;

import com.challengeaccepted.models.LoginModel;
import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.services.UserService;
import com.challengeaccepted.services.YubicoService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final YubicoService yubicoService;

    private final static Logger logger = Logger.getLogger(ChallengeController.class);

    @Autowired
    public UserController(UserService userService, YubicoService yubicoService) {
        this.userService = userService;
        this.yubicoService = yubicoService;
    }

    @CrossOrigin
    @RequestMapping(value = "/google-users", method = RequestMethod.POST)
    public ResponseEntity<UserModel> createGoogleUser(@RequestBody String googleIdToken) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = userService.validateGoogleIdToken(googleIdToken);
        if (idToken != null) {
            UserModel googleUser = userService.createGoogleUser(idToken);
            if (userService.getUserByEmailFromDatabase(googleUser.getEmail()) == null) {
                userService.saveUserToDatabase(googleUser);
                return new ResponseEntity<>(googleUser, HttpStatus.CREATED);
            } else {
             return new ResponseEntity<>(googleUser, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @CrossOrigin
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) throws Exception {
        if (userService.getUserByEmailFromDatabase(userModel.getEmail()) == null) {
            if (userModel.getYubiKeyModel() == null) {
                userService.saveUserToDatabase(userModel);
                logger.info("A new user named " + userModel.getFirstName() + " " + userModel.getLastName() + " has been saved to the database");
                return new ResponseEntity<>(userModel, HttpStatus.CREATED);
            } else {
                String otp = userModel.getYubiKeyModel().getOtp();
                if (yubicoService.validateOtp(otp)) {
                    userModel = yubicoService.setPublicKey(userModel, otp);
                    userService.saveUserToDatabase(userModel);
                    logger.info("A new user named " + userModel.getFirstName() + " " + userModel.getLastName() + " has been saved to the database");
                    return new ResponseEntity<>(userModel, HttpStatus.CREATED);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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
    public ResponseEntity<UserModel> validateLocalLogin(@RequestBody LoginModel loginModel) throws YubicoVerificationException, YubicoValidationFailure, InvalidKeySpecException, NoSuchAlgorithmException {
        UserModel userModel = userService.getUserByEmailFromDatabase(loginModel.getEmail());

        if (userService.validatePassword(userModel.getPassword(), loginModel.getPassword()) &&
                yubicoService.getResponse(loginModel.getOtp()).isOk() &&
                yubicoService.validateYubiKeyID(loginModel.getOtp(), userModel.getYubiKeyModel().getYubiKeyId())) {
            return new ResponseEntity<>(userModel, HttpStatus.OK);
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