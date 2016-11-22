package com.challengeaccepted.services;

import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.models.YubiKeyModel;
import com.challengeaccepted.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    final static Logger logger = Logger.getLogger(UserService.class);

    public void saveUserToDatabase(UserModel userModel) {
        if (userModel.getYubiKeyID() != null) {
            logger.info("User created with Yubico. Store YubiKey in database.");
            userModel.setYubiKeyModel(new YubiKeyModel(userModel, userModel.getYubiKeyID()));
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

    public boolean validatePassword(String userModelPassword, String loginModelPassword) {
        return userModelPassword.equals(loginModelPassword);
    }
}