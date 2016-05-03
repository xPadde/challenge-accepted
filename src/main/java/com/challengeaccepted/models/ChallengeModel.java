package com.challengeaccepted.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "challenges")
public class ChallengeModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private UserModel challengeCreator;
    @OneToOne
    private UserModel challengeClaimer;
    private String topic;
    private String description;
    private String youtubeURL;
    private LocalDateTime creationDate;
    private Long upvotes;
    @OneToMany(cascade = CascadeType.ALL)
    private ArrayList<CommentModel> listOfComments;
    private Boolean isChallengeClaimed;
    private Boolean isYoutubeVideoUploaded;
    private Boolean isYoutubeVideoCorrect;
    private Boolean isChallengeCompleted;

    public ChallengeModel() {
    }

    public Long getId() {
        return id;
    }

    public ArrayList<CommentModel> getListOfComments() {
        return listOfComments;
    }

    public void setListOfComments(ArrayList<CommentModel> listOfComments) {
        this.listOfComments = listOfComments;
    }

    public UserModel getChallengeCreator() {
        return challengeCreator;
    }

    public void setChallengeCreator(UserModel challengeCreator) {
        this.challengeCreator = challengeCreator;
    }

    public UserModel getChallengeClaimer() {
        return challengeClaimer;
    }

    public void setChallengeClaimer(UserModel challengeClaimer) {
        this.challengeClaimer = challengeClaimer;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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