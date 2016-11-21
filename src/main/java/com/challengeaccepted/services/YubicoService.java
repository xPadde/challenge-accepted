package com.challengeaccepted.services;

import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;

public class YubicoService {

    private static YubicoClient client = YubicoClient.getClient(30796, "QXjIQVpZ0CYk21GxaLpZmdoVsxc=");

    public static VerificationResponse getResponse(String otp) throws YubicoVerificationException, YubicoValidationFailure {
        VerificationResponse response = client.verify(otp);
        return response;
    }
}
