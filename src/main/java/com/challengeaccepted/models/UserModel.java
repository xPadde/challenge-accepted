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
    private String password;
    private Double completedChallengePoints;
    private Double createdChallengePoints;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "challengeUpvoters")
    private List<ChallengeModel> upvotedChallenges;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "challengeClaimer")
    private List<ChallengeModel> claimedChallenges;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "challengeCreator")
    private List<ChallengeModel> createdChallenge;

    public UserModel() {
    }

    public Double getCompletedChallengePoints() {
        return completedChallengePoints;
    }

    public void setCompletedChallengePoints(Double completedChallengePoints) {
        this.completedChallengePoints = completedChallengePoints;
    }

    public void addCompletedChallengePoints(Double points) {
        if (this.completedChallengePoints == null) {
            this.completedChallengePoints = 0.0;
        }
        this.completedChallengePoints += points;
    }

    public void removeCompletedChallengePoint(Double points) {
        this.completedChallengePoints -= points;
    }

    public Double getCreatedChallengePoints() {
        return createdChallengePoints;
    }

    public void setCreatedChallengePoints(Double createdChallengePoints) {
        this.createdChallengePoints = createdChallengePoints;
    }

    public void addCreatedChallengePoints(Double points) {
        if (this.createdChallengePoints == null) {
            this.createdChallengePoints = 0.0;
        }
        this.createdChallengePoints += points;
    }

    public void removeCreatedChallengePoint(Double points) {
        this.createdChallengePoints -= points;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}