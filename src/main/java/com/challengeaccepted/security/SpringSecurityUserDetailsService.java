package com.challengeaccepted.security;


import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    private final Logger logger = Logger.getLogger(SpringSecurityUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String[] split = email.split(":");
        String emailCred = split[0];
        String yubicoKey = split[1];

        UserModel userModelFromDataBase = userService.getUserByEmailFromDatabase(emailCred);

        if(userModelFromDataBase == null) {
            throw new UsernameNotFoundException("User not found");
        }

        //TODO write a function that compares the already salthashed password with the password from the
        // database, right now it is in plain text versus the password in database.

        AccountUserDetails userDetails = new AccountUserDetails(userModelFromDataBase);

        //TODO validate login by own services
        return userDetails;

    }

}