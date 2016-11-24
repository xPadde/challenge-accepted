package com.challengeaccepted.security;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;

public class YubicoParamAuthenticationFilter  extends UsernamePasswordAuthenticationFilter{

    @Override
    protected String obtainUsername(HttpServletRequest request) {

        logger.info("-----------------------------------------------obtain körs!!!------------------------------------------");

        String username = request.getParameter(getUsernameParameter());
        String yubicoKey = request.getParameter("yubicoKey");

        return username + ":" + yubicoKey;
    }
}
