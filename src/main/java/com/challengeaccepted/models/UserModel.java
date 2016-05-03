package com.challengeaccepted.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(cascade = CascadeType.ALL, targetEntity = ChallengeModel.class)
    private List requestedChallenges;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = ChallengeModel.class)
    private List claimedChallenges;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = ChallengeModel.class)
    private List upvotedChallenges;

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

    public List getRequestedChallenges() {
        if (requestedChallenges == null) {
            this.requestedChallenges = new ArrayList();
        }
        return requestedChallenges;
    }

    public void setRequestedChallenges(List requestedChallenges) {
        this.requestedChallenges = requestedChallenges;
    }

    public void addRequestedChallenge(ChallengeModel challengeModel) {
        if (requestedChallenges == null) {
            this.requestedChallenges = new ArrayList();
        }
        this.requestedChallenges.add(challengeModel);
    }

    public List getClaimedChallenges() {
        if (this.claimedChallenges == null) {
            this.claimedChallenges = new ArrayList();
        }
        return claimedChallenges;
    }

    public void setClaimedChallenges(List claimedChallenges) {
        this.claimedChallenges = claimedChallenges;
    }

    public void addClaimedChallenge(ChallengeModel challengeModel) {
        if (this.claimedChallenges == null) {
            this.claimedChallenges = new ArrayList();
        }
        this.claimedChallenges.add(challengeModel);
    }

    public List getUpvotedChallenges() {
        if (this.upvotedChallenges == null) {
            this.upvotedChallenges = new ArrayList();
        }
        return upvotedChallenges;
    }

    public void setUpvotedChallenges(List upvotedChallenges) {
        this.upvotedChallenges = upvotedChallenges;
    }

    public void addUpvotedChallenge(ChallengeModel challengeModel) {
        if (this.upvotedChallenges == null) {
            this.upvotedChallenges = new ArrayList();
        }
        this.upvotedChallenges.add(challengeModel);
    }

}