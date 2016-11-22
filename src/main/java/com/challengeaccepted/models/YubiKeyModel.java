package com.challengeaccepted.models;

import javax.persistence.*;

@Entity
@Table(name = "yubikeys")
public class YubiKeyModel {
    @Id
    private Long id;

    @JoinColumn(name = "key_owner")
    @OneToOne
    @MapsId
    private UserModel userModel;

    @Column(name = "yubi_key_id")
    private String yubiKeyId;

    public YubiKeyModel() {
    }

    public YubiKeyModel(UserModel userModel, String yubiKeyId) {
        this.userModel = userModel;
        this.yubiKeyId = yubiKeyId;
    }

    public String getYubiKeyId() {
        return yubiKeyId;
    }

    public void setYubiKeyId(String yubiKeyId) {
        this.yubiKeyId = yubiKeyId;
    }
}
