package com.challengeaccepted.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "challengeUpvoters")
    private List<ChallengeModel> upvotedChallenges;


    public UserModel() {
    }



    public Long getId() {
        return id;
    }

    public List<ChallengeModel> getUpvotedChallenges() {
        return upvotedChallenges;
    }

    public void setUpvotedChallenges(List<ChallengeModel> upvotedChallenges) {
        this.upvotedChallenges = upvotedChallenges;
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