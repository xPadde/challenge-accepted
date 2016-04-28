package com.challengeaccepted.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by joel_ on 2016-04-28.
 */
@Entity
public class CommentModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userModelId;
    private Long challengeModelId;
    private LocalDateTime commentDate;
    private String comment;

    public CommentModel() {
    }

    public Long getUserModelId() {
        return userModelId;
    }

    public void setUserModelId(Long userModelId) {
        this.userModelId = userModelId;
    }

    public Long getChallengeModelId() {
        return challengeModelId;
    }

    public void setChallengeModelId(Long challengeModelId) {
        this.challengeModelId = challengeModelId;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
