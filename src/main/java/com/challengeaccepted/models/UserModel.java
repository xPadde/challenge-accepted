package com.challengeaccepted.models;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String googleTokenId;
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<ChallengeModel> requestedChallenges;
    private ArrayList<ChallengeModel> claimedChallenges;


    public UserModel() {
    }

    public Long getId() {
        return id;
    }

    public String getGoogleTokenId() {
        return googleTokenId;
    }

    public void setGoogleTokenId(String googleTokenId) {
        this.googleTokenId = googleTokenId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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