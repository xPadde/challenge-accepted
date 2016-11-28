package com.challengeaccepted.security;


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

        logger.info("NU VAR ALLT OKEJ ENLiGT SS");

        AccountUserDetails userDetails = new AccountUserDetails(userService.getUserByEmailFromDatabase(emailCred));



        //TODO check is user is null

        //TODO validate login by own services
        return userDetails;

    }

}