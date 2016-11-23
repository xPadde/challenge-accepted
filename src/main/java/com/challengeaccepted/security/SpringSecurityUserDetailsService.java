package com.challengeaccepted.security;


import com.challengeaccepted.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
        logger.info("loadUserByUsername() executed");
        logger.info("email param is: " + email);
        AccountUserDetails userDetails = new AccountUserDetails(userService.getUserByEmailFromDatabase(email));
        logger.info("userDetails username: " + userDetails.getUsername());
        logger.info("userDetails password: " + userDetails.getPassword());

        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            logger.info("userDetails authorities: " + authority.getAuthority());
        }

        logger.info("userDetails enabled? " + userDetails.isEnabled());

        //TODO check is user is null

        // return new AccountUserDetails(userService.getUserByEmailFromDatabase(email));
        return userDetails;
    }

}