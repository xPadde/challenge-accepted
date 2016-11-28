package com.challengeaccepted.services;

import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.models.YubiKeyModel;
import com.challengeaccepted.password.PasswordHash;
import com.challengeaccepted.repositories.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    final static Logger logger = Logger.getLogger(UserService.class);
    final static JacksonFactory jsonFactory = new JacksonFactory();
    final static HttpTransport transport = new NetHttpTransport();

    public void saveUserToDatabase(UserModel userModel) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (userModel.getYubiKeyModel() != null) {
            logger.info("User created with Yubico. Store YubiKey in database.");
            userModel.setYubiKeyModel(new YubiKeyModel(userModel, userModel.getYubiKeyModel().getYubiKeyId()));
            userModel.setPassword(PasswordHash.generateHashedPassword(userModel.getPassword()));
            System.out.println(userModel.getYubiKeyModel().getYubiKeyId());
        }
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

    public GoogleIdToken validateGoogleIdToken(String googleIdToken) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Arrays.asList("483335808440-pn6cvg2jf3iseqmna3rj4883bhkg5lvo.apps.googleusercontent.com"))
                .setIssuer("accounts.google.com")
                .build();

        GoogleIdToken idToken = verifier.verify(googleIdToken);
        if (idToken != null) {
            return idToken;
        } else {
            return null;
        }
    }

    public UserModel createGoogleUser(GoogleIdToken idToken) {
        Payload payload = idToken.getPayload();

        String email = payload.getEmail();
        String firstName = (String) payload.get("given_name");
        String lastName = (String) payload.get("family_name");

        UserModel googleUserModel = new UserModel();
        googleUserModel.setFirstName(firstName);
        googleUserModel.setLastName(lastName);
        googleUserModel.setEmail(email);

        return googleUserModel;
    }
}