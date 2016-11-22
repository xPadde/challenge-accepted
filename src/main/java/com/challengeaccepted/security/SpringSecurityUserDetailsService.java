package com.challengeaccepted.security;


import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class SpringSecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    private final Logger logger = Logger.getLogger(SpringSecurityUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {

        logger.info("inne i backend controllern");
        logger.info("logga user " + user);

        UserModel userModel = userService.getUserByEmailFromDatabase("f");
        logger.info("logga password " + userModel.getPassword());
        //TODO check if null

        //TODO here is the AccountUserDetailsReturn


        return new AccountUserDetails(userModel);

    }
}
