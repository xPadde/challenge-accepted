package com.challengeaccepted.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SpringSecurityUserDetailsService springSecurityUserDetailsService;

    @Autowired
    public void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(springSecurityUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(customUsernamePasswordAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                // TODO authentication works on Postman - not the browser
                    .csrf().disable()
                .authorizeRequests()
                    // .antMatchers("/api/challenges/create/challenge-creator/**").authenticated()
                    // TODO refactor with restricted folders/directories
                    .antMatchers("/views/create-challenge.html").authenticated()
                    .antMatchers("/api/challenges").authenticated()
                    .and()
                .formLogin()
                    .loginPage("/views/login.html")
                    .loginProcessingUrl("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }

    @Bean
    public YubicoParamAuthenticationFilter customUsernamePasswordAuthFilter() throws Exception {
        YubicoParamAuthenticationFilter yubiFilter = new YubicoParamAuthenticationFilter();
        yubiFilter.setAuthenticationManager(authenticationManagerBean());

        return yubiFilter;
    }

}