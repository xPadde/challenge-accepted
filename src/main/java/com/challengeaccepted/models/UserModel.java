package com.challengeaccepted.models;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created by joel_ on 2016-04-29.
 */
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;
    private String password;
    private ArrayList<ChallengeModel> requestedChallenges;
    private ArrayList<ChallengeModel> claimedChallenges;


    public UserModel() {
    }

    public Long getId() {
        return id;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public ArrayList<ChallengeModel> getClaimedChallenges() {
        return claimedChallenges;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public ArrayList<ChallengeModel> getRequestedChallenges() {
        return requestedChallenges;
    }
}
