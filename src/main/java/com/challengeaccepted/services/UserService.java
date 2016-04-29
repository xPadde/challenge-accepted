package com.challengeaccepted.services;

import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by joel_ on 2016-04-29.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUserToDatabase(UserModel userModel) {
        userRepository.saveAndFlush(userModel);
    }

    public UserModel getUserFromDatabase(Long id) {
        return userRepository.findOne(id);
    }
}
