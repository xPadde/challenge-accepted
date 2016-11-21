package com.challengeaccepted.services;

import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;

public class YubicoService {

    private YubicoClient client = YubicoClient.getClient(30796, "QXjIQVpZ0CYk21GxaLpZmdoVsxc=");

    public VerificationResponse getResponse(String otp) throws YubicoVerificationException, YubicoValidationFailure {
        VerificationResponse response = client.verify(otp);
        return response;
    }

    public boolean validateYubiKeyID(String otp, String yubiKeyID) {
        return YubicoClient.getPublicId(otp).equals(yubiKeyID);
    }
}
