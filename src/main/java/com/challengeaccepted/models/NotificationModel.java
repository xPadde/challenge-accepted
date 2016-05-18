package com.challengeaccepted.models;

import com.challengeaccepted.models.enums.Action;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class NotificationModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dateTimeOfNotification;
    @ManyToOne
    @JoinColumn(name = "interactor_id")
    private UserModel interactor;
    @ManyToOne
    @JoinColumn(name = "interacted_challenge_id")
    private ChallengeModel interactedChallenge;
    @Enumerated(EnumType.STRING)
    private Action action;

    public NotificationModel() {
    }

    public NotificationModel(UserModel interactor, ChallengeModel interactedChallenge, Action action) {
        this.interactor = interactor;
        this.interactedChallenge = interactedChallenge;
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTimeOfNotification() {
        return dateTimeOfNotification;
    }

    public void setDateTimeOfNotification(LocalDateTime dateTimeOfNotification) {
        this.dateTimeOfNotification = dateTimeOfNotification;
    }

    public UserModel getInteractor() {
        return interactor;
    }

    public void setInteractor(UserModel interactor) {
        this.interactor = interactor;
    }

    public ChallengeModel getInteractedChallenge() {
        return interactedChallenge;
    }

    public void setInteractedChallenge(ChallengeModel interactedChallenge) {
        this.interactedChallenge = interactedChallenge;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

}