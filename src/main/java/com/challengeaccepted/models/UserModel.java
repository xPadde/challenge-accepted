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
    private Long points;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "challengeUpvoters")
    private List<ChallengeModel> upvotedChallenges;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "challengeClaimer")
    private List<ChallengeModel> claimedChallenges;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "challengeCreator")
    private List<ChallengeModel> createdChallenge;

    public UserModel() {
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public void addPoints(Long points) {
        this.points += points;
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

}