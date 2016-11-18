package com.challengeaccepted.security;

import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private Logger logger = Logger.getLogger(UserDetailServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByEmail(email);

        logger.info("----------------------------------arrived at loadByUsername----------------------------------");

        if(userModel != null) {
            logger.info(userModel.toString());
        }
//
//        AccountUserDetails acc = new AccountUserDetails(userModel);
//
//        Collection<? extends GrantedAuthority> authorities = acc.getAuthorities();
//
//        logger.info("nu är acc user: " + acc);

        //TODO validate login

        // TODO add user not found exception.
        return new AccountUserDetails(userModel);
    }

}