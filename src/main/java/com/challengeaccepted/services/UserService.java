package com.challengeaccepted.services;

import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.password.PasswordHash;
import com.challengeaccepted.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    final static Logger logger = Logger.getLogger(UserService.class);

    public void saveUserToDatabase(UserModel userModel) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (userModel.getPassword() != null) // Only if local account.
            userModel.setPassword(PasswordHash.generateHashedPassword(userModel.getPassword()));

        userRepository.saveAndFlush(userModel);
    }

    public UserModel getUserFromDatabase(Long id) {
        return userRepository.findOne(id);
    }

    public void updateUserInDatabase(UserModel userModelFromWeb) {
        userRepository.save(userModelFromWeb);
    }

    public ArrayList<UserModel> getAllUsersFromDatabase() {
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    public UserModel getUserByEmailFromDatabase(String email) {
        logger.info("Total number of users: " + userRepository.findAll().size());
        return userRepository.findByEmail(email);
    }

    public boolean validatePassword(String userModelPassword, String loginModelPassword) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return PasswordHash.validateHashedPassword(loginModelPassword, userModelPassword);
    }

    public void validateSpringAccount(UserModel userModel) throws IOException {
        logger.info("userModel getEmail: " + userModel.getEmail());

        // Create Spring login endpoint
        // TODO create a dynamic root URL. Do not hardcore localhost.
        String springLoginEndpoint = "http://localhost:8080/challengeaccepted/login";
        URL url = new URL(springLoginEndpoint);

        // Create HTTP request
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Handle params
        String urlParams = "username=" + userModel.getEmail() + "&password=" + userModel.getPassword();
        logger.info("Post request url: " + url + urlParams);
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(urlParams);
        outputStream.flush();
        outputStream.close();

        // Log response code
        logger.info("Sending POST request to URL: " + url);
        int responseCode = connection.getResponseCode();
        logger.info("Got response code from validateSpringAccount(): " + responseCode);
    }

}