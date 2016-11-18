package com.challengeaccepted.security;


import com.challengeaccepted.repositories.UserRepository;
import com.challengeaccepted.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthSuccess authSuccess;

    @Autowired
    private AuthFailure authFailure;

    @Autowired
    private EntryPointUnathorizedHandler unathorizedHandler;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    public void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .csrf()
                    .disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(unathorizedHandler)
                .and()
                    .formLogin()
                    .successHandler(authSuccess)
                    .failureHandler(authFailure)
                .and()
                    .authorizeRequests()
                    .antMatchers("/index").permitAll()
                    .antMatchers("/create-challenge").hasRole("USER").anyRequest()
                    .authenticated();
    }

}