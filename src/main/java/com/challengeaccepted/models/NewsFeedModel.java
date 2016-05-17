package com.challengeaccepted.models;

import com.challengeaccepted.services.ChallengeService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "challenges")
public class NewsFeedModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private UserModel newsFeedOwner;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedLikedChallenge")
    private List<ChallengeModel> likedChallenges;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedClaimedChallenge")
    private List<ChallengeModel> claimedChallenges;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedCreatedChallenge")
    private List<ChallengeModel> createdChallenges;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedPerformedChallenge")
    private List<ChallengeModel> performedChallenges;


    public UserModel getNewsFeedOwner() {
        return newsFeedOwner;
    }

    public void setNewsFeedOwner(UserModel newsFeedOwner) {
        this.newsFeedOwner = newsFeedOwner;
    }

    public List<ChallengeModel> getLikedChallenges() {
        return likedChallenges;
    }

    public void setLikedChallenges(List<ChallengeModel> likedChallenges) {
        this.likedChallenges = likedChallenges;
    }

    public List<ChallengeModel> getClaimedChallenges() {
        return claimedChallenges;
    }

    public void setClaimedChallenges(List<ChallengeModel> claimedChallenges) {
        this.claimedChallenges = claimedChallenges;
    }

    public List<ChallengeModel> getCreatedChallenges() {
        return createdChallenges;
    }

    public void setCreatedChallenges(List<ChallengeModel> createdChallenges) {
        this.createdChallenges = createdChallenges;
    }

    public List<ChallengeModel> getPerformedChallenges() {
        return performedChallenges;
    }

    public void setPerformedChallenges(List<ChallengeModel> performedChallenges) {
        this.performedChallenges = performedChallenges;
    }
}
