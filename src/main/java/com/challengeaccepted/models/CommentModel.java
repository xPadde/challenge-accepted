package com.challengeaccepted.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
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