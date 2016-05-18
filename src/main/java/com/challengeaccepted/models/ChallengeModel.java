package com.challengeaccepted.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "challenges")
public class ChallengeModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String topic;
    private String description;
    private String youtubeURL;
    private LocalDateTime creationDate;
    private Long upvotes;
    private Long points;
    private Boolean isChallengeClaimed;
    private Boolean isYoutubeVideoUploaded;
    private Boolean isYoutubeUrlProvided;
    private Boolean isChallengeCompleted;
    private Boolean isChallengeDisapproved;

    @ManyToMany
    @JoinColumn(name = "challenge_upvoters_id")
    private List<UserModel> challengeUpvoters;
    @OneToOne
    @JoinColumn(name = "challenge_claimer_id")
    private UserModel challengeClaimer;
    @OneToOne
    @JoinColumn(name = "challenge_creator_id")
    private UserModel challengeCreator;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commentedChallenge")
    private List<CommentModel> challengeComments;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interactedChallenge")
    private List<NotificationModel> interactedChallenges;

    public ChallengeModel() {
    }

    public void addUserModelToChallengeUpvoters(UserModel userModel) {
        if (challengeUpvoters == null) {
            challengeUpvoters = new ArrayList<UserModel>();
        }
        challengeUpvoters.add(userModel);
    }

    public void removeUserModelFromChallengeUpvoters(UserModel userModel) {
        challengeUpvoters.remove(userModel);
    }

    public List<Long> getChallengeUpvoters() {
        List<Long> listOfChallengeUpvotersId = new ArrayList<Long>();
        for (UserModel user : challengeUpvoters) {
            listOfChallengeUpvotersId.add(user.getId());
        }
        return listOfChallengeUpvotersId;
    }

    public void setChallengeUpvoters(List<UserModel> challengeUpvoters) {
        this.challengeUpvoters = challengeUpvoters;
    }

    public List<Long> getChallengeComments() {
        List<Long> listOfChallengeCommentsId = new ArrayList<Long>();
        for (UserModel user : challengeUpvoters) {
            listOfChallengeCommentsId.add(user.getId());
        }
        return listOfChallengeCommentsId;
    }


    public void setChallengeComments(List<CommentModel> challengeComments) {
        this.challengeComments = challengeComments;
    }

    public Long getId() {
        return id;
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

    public void addUpvote() {
        if (this.upvotes == null) {
            this.upvotes = 0L;
        }
        this.upvotes += 1L;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public void addPoints(Long points) {
        if (this.points == null) {
            this.points = 0L;
        }
        this.points += points;
    }

    public void removePoint() {
        this.points -= 1L;
    }

    public void removeUpvote() {
        this.upvotes -= 1L;
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

    public Boolean isYoutubeUrlProvided() {
        return isYoutubeUrlProvided;
    }

    public void setYoutubeUrlProvided(Boolean youtubeUrlProvided) {
        isYoutubeUrlProvided = youtubeUrlProvided;
    }

    public Boolean getChallengeCompleted() {
        return isChallengeCompleted;
    }

    public void setChallengeCompleted(Boolean challengeCompleted) {
        isChallengeCompleted = challengeCompleted;
    }

    public Boolean getChallengeDisapproved() {
        return isChallengeDisapproved;
    }

    public void setChallengeDisapproved(Boolean challengeDisapproved) {
        isChallengeDisapproved = challengeDisapproved;
    }

    public UserModel getChallengeCreator() {
        return challengeCreator;
    }

    public void setChallengeCreator(UserModel challengeCreator) {
        this.challengeCreator = challengeCreator;
    }

    public void addCommentToChallenge(CommentModel commentModel) {
        if (challengeComments == null) {
            challengeComments = new ArrayList<CommentModel>();
        }
        challengeComments.add(commentModel);
    }

}