package com.challengeaccepted.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Stridsberg on 2016-04-27.
 */
@Entity
public class ChallengeModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long challengeCreatorId;
    private Long challengeClaimerId;
    private String topic;
    private String description;
    private String youtubeURL;
    private LocalDateTime creationDate;
    private Long upvotes;
    private ArrayList<CommentModel> listOfComments;
    private Boolean isChallengeClaimed;
    private Boolean isYoutubeVideoUploaded;
    private Boolean isYoutubeVideoCorrect;
    private Boolean isChallengeCompleted;


    public ChallengeModel() {
    }

    @OneToMany(cascade = CascadeType.ALL)
    public ArrayList<CommentModel> getListOfComments(){
        return listOfComments;
    }

    public void setListOfComments(ArrayList<CommentModel> listOfComments) {
        this.listOfComments = listOfComments;
    }

    public Long getChallengeCreatorId() {
        return challengeCreatorId;
    }

    public void setChallengeCreatorId(Long challengeCreatorId) {
        this.challengeCreatorId = challengeCreatorId;
    }

    public Long getChallengeClaimerId() {
        return challengeClaimerId;
    }

    public void setChallengeClaimerId(Long challengeClaimerId) {
        this.challengeClaimerId = challengeClaimerId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutubeURL() {
        return youtubeURL;
    }

    public void setYoutubeURL(String youtubeURL) {
        this.youtubeURL = youtubeURL;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Long upvotes) {
        this.upvotes = upvotes;
    }

    public Boolean getChallengeClaimed() {
        return isChallengeClaimed;
    }

    public void setChallengeClaimed(Boolean challengeClaimed) {
        isChallengeClaimed = challengeClaimed;
    }

    public Boolean getYoutubeVideoUploaded() {
        return isYoutubeVideoUploaded;
    }

    public void setYoutubeVideoUploaded(Boolean youtubeVideoUploaded) {
        isYoutubeVideoUploaded = youtubeVideoUploaded;
    }

    public Boolean getYoutubeVideoCorrect() {
        return isYoutubeVideoCorrect;
    }

    public void setYoutubeVideoCorrect(Boolean youtubeVideoCorrect) {
        isYoutubeVideoCorrect = youtubeVideoCorrect;
    }

    public Boolean getChallengeCompleted() {
        return isChallengeCompleted;
    }

    public void setChallengeCompleted(Boolean challengeCompleted) {
        isChallengeCompleted = challengeCompleted;
    }
}
