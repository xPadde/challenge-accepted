package com.challengeaccepted.services;

import com.challengeaccepted.models.UserModel;
import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;
import org.springframework.stereotype.Service;

@Service
public class YubicoService {

    private YubicoClient client = YubicoClient.getClient(30796, "QXjIQVpZ0CYk21GxaLpZmdoVsxc=");

    public VerificationResponse getResponse(String otp) throws YubicoVerificationException, YubicoValidationFailure {
        VerificationResponse response = client.verify(otp);
        return response;
    }

    public boolean validateYubiKeyID(String otp, String yubiKeyID) {
        return YubicoClient.getPublicId(otp).equals(yubiKeyID);
    }

    public String getPublicKey(String otp) {
        return YubicoClient.getPublicId(otp);
    }

    public boolean validateOtp(String otp) throws YubicoVerificationException, YubicoValidationFailure {
        return getResponse(otp).isOk();
    }

    public UserModel setPublicKey(UserModel userModel, String otp) {
        userModel.getYubiKeyModel().setYubiKeyId(getPublicKey(otp));
        return userModel;
    }
}
