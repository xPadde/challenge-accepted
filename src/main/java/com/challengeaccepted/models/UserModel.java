package com.challengeaccepted.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long totalChallengePoints;
    private Long completedChallengePoints;
    private Long createdChallengePoints;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "challengeUpvoters")
    private List<ChallengeModel> upvotedChallenges;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "challengeClaimer")
    private List<ChallengeModel> claimedChallenges;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "challengeCreator")
    private List<ChallengeModel> createdChallenge;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interactor")
    private List<NotificationModel> interactors;

    public UserModel() {
    }

    public Long getTotalChallengePoints() {
        return totalChallengePoints;
    }

    public void setTotalChallengePoints(Long totalChallengePoints) {
        this.totalChallengePoints = totalChallengePoints;
    }

    public void addTotalChallengePoints(Long points) {
        if (this.totalChallengePoints == null) {
            this.totalChallengePoints = 0L;
        }
        this.totalChallengePoints += points;
    }

    public Long getCompletedChallengePoints() {
        return completedChallengePoints;
    }

    public void setCompletedChallengePoints(Long completedChallengePoints) {
        this.completedChallengePoints = completedChallengePoints;
    }

    public void addCompletedChallengePoints(Long points) {
        if (this.completedChallengePoints == null) {
            this.completedChallengePoints = 0L;
        }
        this.completedChallengePoints += points;
        addTotalChallengePoints(points);
    }

    public Long getCreatedChallengePoints() {
        return createdChallengePoints;
    }

    public void setCreatedChallengePoints(Long createdChallengePoints) {
        this.createdChallengePoints = createdChallengePoints;
    }

    public void addCreatedChallengePoints(Long points) {
        if (this.createdChallengePoints == null) {
            this.createdChallengePoints = 0L;
        }
        this.createdChallengePoints += points;
        addTotalChallengePoints(points);
    }

    public Long getId() {
        return id;
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

    public List<NotificationModel> getInteractors() {
        return interactors;
    }

    public void setInteractors(List<NotificationModel> interactors) {
        this.interactors = interactors;
    }

}