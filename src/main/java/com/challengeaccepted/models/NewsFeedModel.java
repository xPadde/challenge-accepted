package com.challengeaccepted.models;

import com.challengeaccepted.services.ChallengeService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "newsfeed")
public class NewsFeedModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedLikedChallenge")
    private List<ChallengeModel> likedChallenges;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedClaimedChallenge")
    private List<ChallengeModel> claimedChallenges;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedCreatedChallenge")
    private List<ChallengeModel> createdChallenges;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedPerformedChallenge")
    private List<ChallengeModel> performedChallenges;

    public NewsFeedModel() {
    }

    public Long getId() {
        return id;
    }

    public List<ChallengeModel> getLikedChallenges() {
        return likedChallenges;
    }

    public void setLikedChallenges(List<ChallengeModel> likedChallenges) {
        this.likedChallenges = likedChallenges;
    }

    public void addLikedChallenge(ChallengeModel likedChallenge){
        if(likedChallenges == null){
            likedChallenges = new ArrayList<ChallengeModel>();
        }
        likedChallenges.add(likedChallenge);
    }

    public void removeLikedChallenge(ChallengeModel challenge) {
        likedChallenges.remove(challenge);
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
