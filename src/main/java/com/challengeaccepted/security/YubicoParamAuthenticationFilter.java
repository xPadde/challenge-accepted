package com.challengeaccepted.security;

import com.challengeaccepted.password.PasswordHash;
import com.challengeaccepted.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class YubicoParamAuthenticationFilter  extends UsernamePasswordAuthenticationFilter{

    @Autowired
    private UserService userService;

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String username = request.getParameter(getUsernameParameter());
        String yubicoKey = request.getParameter("yubicoKey");

        return username + ":" + yubicoKey;
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        String username = request.getParameter(getUsernameParameter());
        String password = request.getParameter(getPasswordParameter());

        String passwordFromDatabase = userService.getUserByEmailFromDatabase(username).getPassword();

        String[] passwordSplitted = passwordFromDatabase.split(":");
        String salt = passwordSplitted[1];

        logger.info("User password as HASH: " + passwordFromDatabase);
        logger.info("password parameter: " + password);
        logger.info("username parameter: " + username);

        String passwordToValidate = "";
        try {
            passwordToValidate = PasswordHash.generatePasswordForSpringSecValidation(password, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        logger.info("passwordToValidate (Spring): " + passwordToValidate);
        logger.info("password (from database): " + passwordFromDatabase);

        return passwordToValidate;
    }

}